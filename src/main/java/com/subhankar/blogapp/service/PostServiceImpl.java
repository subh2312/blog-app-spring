package com.subhankar.blogapp.service;

import com.subhankar.blogapp.dao.PostDao;
import com.subhankar.blogapp.dto.PostDto;
import com.subhankar.blogapp.utils.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostDao postDao;
    @Override
    public PostDto addAPost(PostDto postDto) {
        return postDao.addAPost(postDto);
    }

    @Override
    public PaginatedResponse getAllPosts(int pageNo, int pageSize,String sortBy, String sortDir) {
        return postDao.getAllPosts(pageNo, pageSize,sortBy,sortDir);
    }

    @Override
    public PostDto getAPost(Long id) {
        return postDao.getAPost(id);
    }

    @Override
    public PostDto updateAPost(PostDto postDto, Long id) {
        return postDao.updateAPost(postDto,id);
    }

    @Override
    public String deleteAPost(Long id) {
        return postDao.deleteAPost(id);
    }
}
