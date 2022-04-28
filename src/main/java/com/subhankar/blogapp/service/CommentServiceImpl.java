package com.subhankar.blogapp.service;

import com.subhankar.blogapp.dao.CommentDao;
import com.subhankar.blogapp.dto.CommentDto;
import com.subhankar.blogapp.entity.Comment;
import com.subhankar.blogapp.utils.PaginatedCommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentDao commentDao;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        return commentDao.createComment(postId,commentDto);
    }

    @Override
    public PaginatedCommentResponse getCommentsByPostId(Long postId,int pageNo, int pageSize,String sortBy, String sortDir) {

        return commentDao.getCommentsByPostId(postId,pageNo, pageSize,sortBy,sortDir);
    }

    @Override
    public CommentDto getCommentById(long id) {
        return commentDao.getCommentById(id);
    }

    @Override
    public CommentDto updateComment(long postId, CommentDto commentDto, long id) {
        return commentDao.updateComment(postId,commentDto,id);
    }

    @Override
    public void deleteComment(long postId, long id) {
        commentDao.deleteComment(postId,id);
    }
}
