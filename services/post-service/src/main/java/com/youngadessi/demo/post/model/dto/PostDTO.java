package com.youngadessi.demo.post.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class PostDTO implements Serializable {

    private String createdByName;

    private String content;

}
