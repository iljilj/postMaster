package com.example.hop.service;


import com.example.hop.dto.PostDto;
import com.example.hop.model.Post;

import java.util.List;

public interface PostService {

    Post findPostById(Long id);

    PostDto findPostDtoById(Long id);

    void create(PostDto postDto);

    void delete(Long id);

    List<PostDto> findAll();

    void edit(PostDto postDto);

}
