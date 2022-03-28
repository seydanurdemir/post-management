package com.youngadessi.demo.post.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class PostRequestDTO implements Serializable {

    @NotBlank
    private String createdByName;

}
