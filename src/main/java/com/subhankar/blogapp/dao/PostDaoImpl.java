package com.subhankar.blogapp.dao;

import com.subhankar.blogapp.dto.PostDto;
import com.subhankar.blogapp.entity.Post;
import com.subhankar.blogapp.exception.ResourceNotFoundException;
import com.subhankar.blogapp.repository.PostRepository;
import com.subhankar.blogapp.utils.PaginatedResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostDaoImpl implements PostDao{
    private PostRepository repository;

    private ModelMapper modelMapper;

    public PostDaoImpl(PostRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto addAPost(PostDto postDto) {
        return modelMapper.map(repository.save(modelMapper.map(postDto, Post.class)),PostDto.class);
    }

    @Override
    public PaginatedResponse getAllPosts(int pageNo, int pageSize,String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }
        else{
            sort = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> posts = repository.findAll(pageable);

        List<Post> postList = posts.getContent();
        List<PostDto> content =postList.stream().map(p->modelMapper.map(p,PostDto.class)).collect(Collectors.toList());
        PaginatedResponse response = new PaginatedResponse();
        response.setPosts(content);
        response.setPageNo(posts.getNumber());
        response.setPageSize(posts.getSize());
        response.setTotalElements(posts.getTotalElements());
        response.setTotalPages(posts.getTotalPages());
        response.setLast(posts.isLast());
        return response;
    }

    @Override
    public PostDto getAPost(Long id) {
        return modelMapper.map(repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",String.valueOf(id))),PostDto.class);
    }

    @Override
    public PostDto updateAPost(PostDto postDto, Long id) {
        PostDto postDto1 = modelMapper.map(repository.findById(id).get(),PostDto.class);
        postDto1.setContent(postDto.getContent());
        postDto1.setDescription(postDto.getDescription());
        postDto1.setTitle(postDto.getTitle());
        repository.save(modelMapper.map(postDto1,Post.class));
        return postDto1;
    }

    @Override
    public String deleteAPost(Long id) {
        PostDto postDto = modelMapper.map(repository.findById(id).get(),PostDto.class);
        repository.delete(modelMapper.map(postDto,Post.class));
        return "Post Deleted Successfully";
    }
}
