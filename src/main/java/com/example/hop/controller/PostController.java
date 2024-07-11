package com.example.hop.controller;

import com.example.hop.dto.PostDto;
import com.example.hop.model.Post;
import com.example.hop.service.PostService;
import com.example.hop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        PostDto postDto = new PostDto();
        postDto.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("post", postDto);
        return "new_post";
    }

    @PostMapping("/new")
    public String newPost(@ModelAttribute("post") @Valid PostDto postDto, BindingResult bindingResult) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userService.findByName(username).isBanned()){
            return "redirect:/user";
        }
        postDto.setUsername(username);
        if (bindingResult.hasErrors()) {
            return "new_post";
        }

        postService.create(postDto);
        return "redirect:/user";
    }

    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId, Model model, @RequestParam("referrer") String referrer) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postService.findPostById(postId);

        if (post == null) {
            model.addAttribute("error", "Post not found.");
            return "redirect:/error";
        }
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (post.getUser().getName().equals(currentUser) || isAdmin) {
            postService.delete(postId);
            return "redirect:" + referrer;
        } else {
            model.addAttribute("error", "You are not authorized to delete this post.");
            return "redirect:/unauthorized";
        }
    }

    @GetMapping("/edit/{postId}")
    public String editPost(Model model, @PathVariable Long postId) {
        PostDto postDto = postService.findPostDtoById(postId);
        if (postDto == null) {
            model.addAttribute("error", "Post not found.");
            return "redirect:/error";
        }
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!postDto.getUsername().equals(loggedInUser)) {
            model.addAttribute("error", "You are not authorized to edit this post.");
            return "redirect:/unauthorized";
        }
        model.addAttribute("post", postDto);
        return "edit_post";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("post") @Valid PostDto postDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postDto);
            return "edit_post";
        }
        postService.edit(postDto);
        return "redirect:/user";
    }
}
