package com.example.hop.controller;

import com.example.hop.dto.PasswordChange;
import com.example.hop.dto.UserDto;
import com.example.hop.service.PostService;
import com.example.hop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String userHome(RedirectAttributes redirectAttributes) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        redirectAttributes.addAttribute("username", username);
        return "redirect:/user/{username}";
    }

    @GetMapping("/{username}")
    public String viewUser(@PathVariable String username, Model model, HttpServletRequest request) {
        model.addAttribute("currentURI", request.getRequestURI());
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("currentUser", currentUser);
        boolean isCurrentUserAdmin = userService.findByName(currentUser).isAdmin();
        model.addAttribute("isCurrentUserAdmin", isCurrentUserAdmin);
        UserDto userDto = userService.findByName(username);
        model.addAttribute("user", userDto);
        return "user";
    }

    @GetMapping("/delete")
    public String deleteUser(Model model) {
        UserDto userDto = userService.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", userDto);
        return "delete_account";
    }

    @PostMapping("/delete")
    public String deleteUser(HttpServletRequest request, HttpServletResponse response) {
        Long userId = userService.getIdByName(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.delete(userId);
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

    @GetMapping("/updatePassword")
    public String updatePasswordForm(Model model) {
        PasswordChange passwordChange = new PasswordChange();
        model.addAttribute("passwordChange", passwordChange);
        return "update_password";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@ModelAttribute("passwordChange") @Valid PasswordChange passwordChange, BindingResult result) {
        Long userId = userService.getIdByName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (result.hasErrors()) {
            return "update_password";
        }
        if (!userService.updatePassword(userId, passwordChange)){
            result.rejectValue("oldPassword", "error.passwordChange", "Wrong old password");
            return "update_password";
        } else {
            return "redirect:/user";
        }
    }
}
