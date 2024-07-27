package com.example.hop.controller;

import com.example.hop.dto.PostDto;
import com.example.hop.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(FeedController.class)
public class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void feedPage_ShouldReturnFeedPage() throws Exception {
        List<PostDto> posts = Arrays.asList(new PostDto(1L, "Test Title", "Test Content", "username", LocalDateTime.now()));
        when(postService.findAll()).thenReturn(posts);

        mockMvc.perform(get("/feed"))
                .andExpect(status().isOk())
                .andExpect(view().name("feed"))
                .andExpect(model().attributeExists("currentURI"))
                .andExpect(model().attributeExists("currentUser"))
                .andExpect(model().attributeExists("isCurrentUserAdmin"))
                .andExpect(model().attribute("posts", posts));
    }
}
