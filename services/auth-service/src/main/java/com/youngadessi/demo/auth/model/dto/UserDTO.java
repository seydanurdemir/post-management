package com.youngadessi.demo.auth.model.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
