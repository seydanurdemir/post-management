package com.youngadessi.demo.post.model.dto;

import com.youngadessi.demo.post.model.entity.Post;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO implements Serializable {

    private Post post;

    private String commentText;

}
