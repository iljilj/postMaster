package com.example.hop.controller;

import com.example.hop.dto.PostDto;
import com.example.hop.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    PostService postService;

    @GetMapping
    public String feedPage(Model model, HttpServletRequest request) {
        model.addAttribute("currentURI", request.getRequestURI());
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("currentUser", currentUser);
        boolean isCurrentUserAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isCurrentUserAdmin", isCurrentUserAdmin);
        List<PostDto> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "feed";
    }
}
