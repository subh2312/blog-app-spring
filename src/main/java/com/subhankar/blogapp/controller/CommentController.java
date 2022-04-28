package com.subhankar.blogapp.controller;

import com.subhankar.blogapp.dto.CommentDto;
import com.subhankar.blogapp.service.CommentService;
import com.subhankar.blogapp.utils.AppConstants;
import com.subhankar.blogapp.utils.PaginatedCommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<CommentDto>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public PaginatedCommentResponse getCommentsByPostId(@PathVariable(name = "postId") Long postId,
                                                        @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                        @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return commentService.getCommentsByPostId(postId,pageNo, pageSize,sortBy,sortDir);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name="id") long id){
        return new ResponseEntity<>(commentService.getCommentById(id),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name="postId")long postId,@Valid @RequestBody CommentDto commentDto, @PathVariable(name="id") long id){
        return new ResponseEntity<>(commentService.updateComment(postId,commentDto,id),HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(name="postId")long postId, @PathVariable(name="id") long id){
        commentService.deleteComment(postId,id);
        return new ResponseEntity<>("Post Deleted Successfully",HttpStatus.OK);
    }
}
