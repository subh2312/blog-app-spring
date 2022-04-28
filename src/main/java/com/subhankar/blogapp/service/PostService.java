package com.subhankar.blogapp.service;

import com.subhankar.blogapp.dto.PostDto;
import com.subhankar.blogapp.utils.PaginatedResponse;

import java.util.List;

public interface PostService {
    PostDto addAPost(PostDto postDto);

    PaginatedResponse getAllPosts(int pageNo, int pageSize,String sortBy, String sortDir);

    PostDto getAPost(Long id);

    PostDto updateAPost(PostDto postDto, Long id);

    String deleteAPost(Long id);
}
