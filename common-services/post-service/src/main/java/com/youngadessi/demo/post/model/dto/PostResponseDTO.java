package com.youngadessi.demo.post.model.dto;

import com.youngadessi.demo.post.model.entity.Comment;
import com.youngadessi.demo.post.model.entity.Tag;

import lombok.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PostResponseDTO implements Serializable {

    private String createdByName;

    private String content;

    private List<Comment> postComments;

    private List<Tag> postTags;

}
