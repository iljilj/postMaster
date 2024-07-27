package com.example.hop.controller;

import com.example.hop.dto.PostDto;
import com.example.hop.service.PostService;
import com.example.hop.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void newPost_ShouldReturnNewPostPage() throws Exception {
        mockMvc.perform(get("/user/posts/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("new_post"))
                .andExpect(model().attributeExists("post"));
    }


    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void editPost_ShouldReturnEditPostPage() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setUsername("testuser");
        when(postService.findPostDtoById(1L)).thenReturn(postDto);

        mockMvc.perform(get("/user/posts/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit_post"))
                .andExpect(model().attributeExists("post"));
    }


}
