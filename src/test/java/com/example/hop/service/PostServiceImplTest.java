package com.example.hop.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.hop.repository.PostRepository;
import com.example.hop.repository.UserRepository;
import com.example.hop.model.Post;
import com.example.hop.model.User;
import com.example.hop.service.PostServiceImpl;
import com.example.hop.dto.PostDto;

import java.util.Optional;

public class PostServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePost() {
        PostDto postDto = new PostDto();
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");
        postDto.setUsername("testUser");

        User user = new User();
        user.setName("testUser");

        when(userRepository.findByName("testUser")).thenReturn(user);

        postService.create(postDto);

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testFindPostById() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Post foundPost = postService.findPostById(1L);

        assertNotNull(foundPost);
        assertEquals("Test Title", foundPost.getTitle());
    }
}
