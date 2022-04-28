package com.subhankar.blogapp.utils;

import com.subhankar.blogapp.dto.CommentDto;
import com.subhankar.blogapp.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedCommentResponse {
    private List<CommentDto> comments;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
