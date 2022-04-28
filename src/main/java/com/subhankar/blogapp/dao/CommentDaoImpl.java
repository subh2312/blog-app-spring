package com.subhankar.blogapp.dao;

import com.subhankar.blogapp.dto.CommentDto;
import com.subhankar.blogapp.dto.PostDto;
import com.subhankar.blogapp.entity.Comment;
import com.subhankar.blogapp.entity.Post;
import com.subhankar.blogapp.exception.ResourceNotFoundException;
import com.subhankar.blogapp.repository.CommentRepository;
import com.subhankar.blogapp.repository.PostRepository;
import com.subhankar.blogapp.utils.PaginatedCommentResponse;
import com.subhankar.blogapp.utils.PaginatedResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentDaoImpl implements CommentDao {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",String.valueOf(postId)));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        commentRepository.save(comment);
        return commentDto;
    }

    @Override
    public PaginatedCommentResponse getCommentsByPostId(Long postId,int pageNo, int pageSize,String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }
        else{
            sort = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Comment> comments = commentRepository.findByPostId(postId,pageable);

        List<Comment> postList = comments.getContent();
        List<CommentDto> content =postList.stream().map(p->modelMapper.map(p,CommentDto.class)).collect(Collectors.toList());
        PaginatedCommentResponse response = new PaginatedCommentResponse();
        response.setComments(content);
        response.setPageNo(comments.getNumber());
        response.setPageSize(comments.getSize());
        response.setTotalElements(comments.getTotalElements());
        response.setTotalPages(comments.getTotalPages());
        response.setLast(comments.isLast());
        return response;
    }

    @Override
    public CommentDto getCommentById(long id) {
        return modelMapper.map(commentRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Comment","id",String.valueOf(id))),
                CommentDto.class);
    }

    @Override
    public CommentDto updateComment(long postId, CommentDto commentDto, long id) {
        if(!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post","id",String.valueOf(postId));
        }
        Comment comment = commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Comment","id",String.valueOf(id)));
        Comment newComment = modelMapper.map(commentDto,Comment.class);
        comment.setName(newComment.getName());
        comment.setEmail(newComment.getEmail());
        comment.setMessage(newComment.getMessage());

        commentRepository.save(comment);

        return modelMapper.map(comment,CommentDto.class);
    }

    @Override
    public void deleteComment(long postId, long id) {
        Comment comment=commentRepository.findByIdAndPostId(id,postId).orElseThrow(()->new ResourceNotFoundException("Comment","comment id",String.valueOf(id)));

        commentRepository.delete(comment);


    }
}
