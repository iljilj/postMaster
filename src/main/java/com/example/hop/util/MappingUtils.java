package com.example.hop.util;

import com.example.hop.dto.PostDto;
import com.example.hop.dto.UserDto;
import com.example.hop.model.Post;
import com.example.hop.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {

    private MappingUtils() {
    }

    public static UserDto mapUserToUserDto(User user) {
        UserDto userDto = null;
        if (user != null) {
            userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setAdmin(user.getRoles().stream()
                    .anyMatch(role -> role.getName().equals("ROLE_ADMIN")));
            userDto.setBanned(user.isBanned());
            List<PostDto> posts = user.getPosts().stream()
                    .map(MappingUtils::mapPostToPostDto)
                    .collect(Collectors.toList());
            userDto.setPosts(posts);
        }
        return userDto;
    }

    public static PostDto mapPostToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUser().getName());
        postDto.setCreationTime(post.getCreationTime());
        return postDto;
    }
}
