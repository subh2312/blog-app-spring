package com.subhankar.blogapp.dto;

import com.subhankar.blogapp.entity.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    @NotNull
    @Size(min=4)
    @ApiModelProperty(
            readOnly = true,
            value = "title",
            dataType = "string",
            example = "Blog Post title",
            notes= "Breif subject regarding the post"
    )
    private String title;
    @ApiModelProperty(
            readOnly = true,
            value = "description",
            dataType = "string",
            example = "About the Post",
            notes= "Post Description"
    )
    private String description;
    @NotNull
    @ApiModelProperty(
            readOnly = true,
            value = "content",
            dataType = "string",
            example = "About the Book",
            notes= "Post Content"
    )
    private String content;
    private List<Comment> comments;
}
