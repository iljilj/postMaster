package com.example.hop.controller;

import com.example.hop.dto.UserDto;
import com.example.hop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String adminPage(@RequestParam(name = "filter", required = false) String filter,
                            Model model, HttpServletRequest request) {
        String currentURI = request.getRequestURL().toString() + "?" + request.getQueryString();
        model.addAttribute("currentURI", currentURI);
        model.addAttribute("filter", filter);

        if ("admin".equals(filter)) {
            model.addAttribute("users", userService.findAdmins());
        } else if ("banned".equals(filter)) {
            model.addAttribute("users", userService.findBannedUsers());
        } else {
            model.addAttribute("users", userService.findAll());
        }

        return "admin";
    }

    @PostMapping("/banStatus")
    public String ban(@RequestParam("userId") Long userId,
                      @RequestParam("referrer") String referrer) {
        System.out.println("!!" + referrer);
        userService.banStatus(userId);
        return "redirect:" + referrer;
    }

    @PostMapping("/adminStatus")
    public String adminStatus(@RequestParam("userId") Long userId,
                              @RequestParam("referrer") String referrer) {
        System.out.println("!!" + referrer);
        userService.adminStatus(userId);
        return "redirect:" + referrer;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("userId") Long userId) {
        userService.delete(userId);
        return "redirect:/admin";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "create_user";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "create_user";
        }
        userService.create(userDto, result);
        if (result.hasErrors()) {
            return "create_user";
        }
        return "redirect:/admin";
    }
}
