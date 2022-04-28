package com.subhankar.blogapp.controller;

import com.subhankar.blogapp.dto.PostDto;
import com.subhankar.blogapp.service.PostService;
import com.subhankar.blogapp.utils.AppConstants;
import com.subhankar.blogapp.utils.PaginatedResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "Post Api", tags = "Post Api", produces = "application.json")
@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired
    private PostService service;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/posts")
    public ResponseEntity<PostDto> addAPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity(service.addAPost(postDto), HttpStatus.CREATED);

    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiOperation(value="Get List of Posts", response = PostDto[].class, produces = "application.json")
    @GetMapping("/posts")
    public PaginatedResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortOrder
    ){
        return (service.getAllPosts(pageNo, pageSize,sortBy,sortOrder));

    }
    @GetMapping("/posts/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PostDto> getAPost(@PathVariable Long id){
        return new ResponseEntity(service.getAPost(id), HttpStatus.OK);

    }
    @PutMapping("/posts/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PostDto> updateAPost(@Valid @RequestBody PostDto postDto, @PathVariable Long id){
        return new ResponseEntity(service.updateAPost(postDto,id), HttpStatus.OK);

    }
    @DeleteMapping("/posts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addAPost(@PathVariable Long id){
        return new ResponseEntity(service.deleteAPost(id), HttpStatus.OK);

    }
}
