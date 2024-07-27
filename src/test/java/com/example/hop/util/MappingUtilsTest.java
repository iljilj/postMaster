package com.example.hop.util;

import com.example.hop.dto.PostDto;
import com.example.hop.dto.UserDto;
import com.example.hop.model.Post;
import com.example.hop.model.Role;
import com.example.hop.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MappingUtilsTest {

    @Test
    public void testMapUserToUserDto() {
        // Prepare test data
        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        User user = new User();
        user.setId(1L);
        user.setName("testuser");
        user.setPassword("password");
        user.setBanned(false);
        user.getRoles().add(userRole);
        user.getRoles().add(adminRole);

        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Post 1");
        post1.setContent("Content 1");
        post1.setCreationTime(LocalDateTime.now());
        post1.setUser(user);

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Post 2");
        post2.setContent("Content 2");
        post2.setCreationTime(LocalDateTime.now());
        post2.setUser(user);

        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        user.setPosts(posts);

        // Call the method under test
        UserDto userDto = MappingUtils.mapUserToUserDto(user);

        // Verify the result
        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.isBanned(), userDto.isBanned());
        assertEquals(true, userDto.isAdmin());
        assertEquals(2, userDto.getPosts().size());
    }

    @Test
    public void testMapPostToPostDto() {
        // Prepare test data
        User user = new User();
        user.setName("testuser");

        Post post = new Post();
        post.setId(1L);
        post.setTitle("Post 1");
        post.setContent("Content 1");
        post.setCreationTime(LocalDateTime.now());
        post.setUser(user);

        // Call the method under test
        PostDto postDto = MappingUtils.mapPostToPostDto(post);

        // Verify the result
        assertNotNull(postDto);
        assertEquals(post.getId(), postDto.getId());
        assertEquals(post.getTitle(), postDto.getTitle());
        assertEquals(post.getContent(), postDto.getContent());
        assertEquals(post.getUser().getName(), postDto.getUsername());
        assertEquals(post.getCreationTime(), postDto.getCreationTime());
    }
}
