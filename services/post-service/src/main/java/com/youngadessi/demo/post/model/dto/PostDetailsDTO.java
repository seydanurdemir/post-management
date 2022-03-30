package com.youngadessi.demo.post.model.dto;

import com.youngadessi.demo.post.model.entity.Comment;
import com.youngadessi.demo.post.model.entity.Tag;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PostDetailsDTO implements Serializable {

    private String createdByName;

    private String content;

    private List<Comment> postComments;

    private List<Tag> postTags;

}
