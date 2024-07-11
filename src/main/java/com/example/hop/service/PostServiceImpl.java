package com.example.hop.service;


import com.example.hop.dto.PostDto;
import com.example.hop.model.Post;
import com.example.hop.model.User;
import com.example.hop.repository.PostRepository;
import com.example.hop.repository.RoleRepository;
import com.example.hop.repository.UserRepository;
import com.example.hop.util.MappingUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private UserRepository userRepository;
    private PostRepository postRepository;

    public PostServiceImpl(UserRepository userRepository,
                           PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> findAll() {
        return postRepository.findAll().stream().map(MappingUtils::mapPostToPostDto).collect(Collectors.toList());
    }

    @Override
    public void edit(PostDto postDto) {
        System.out.println(postDto.toString());
        Post post = postRepository.findById(postDto.getId()).orElseThrow();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        postRepository.save(post);
    }

    @Override
    public Post findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public PostDto findPostDtoById(Long id) {
        return postRepository.findById(id).map(MappingUtils::mapPostToPostDto).orElse(null);
    }

    @Override
    public void create(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User user = userRepository.findByName(postDto.getUsername());
        post.setUser(user);
        postRepository.save(post);
    }
}
