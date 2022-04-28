package com.subhankar.blogapp.dao;

import com.subhankar.blogapp.dto.CommentDto;
import com.subhankar.blogapp.utils.PaginatedCommentResponse;

import java.util.List;

public interface CommentDao {

    CommentDto createComment(long postId, CommentDto commentDto);

    PaginatedCommentResponse getCommentsByPostId(Long postId,int pageNo, int pageSize,String sortBy, String sortDir);

    CommentDto getCommentById(long id);

    CommentDto updateComment(long postId, CommentDto commentDto, long id);

    void deleteComment(long postId, long id);
}
