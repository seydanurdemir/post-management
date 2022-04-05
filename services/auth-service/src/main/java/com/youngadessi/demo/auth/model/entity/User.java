package com.youngadessi.demo.auth.model.entity;

import com.youngadessi.demo.model.BaseEntity;

import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tbl_user")
public class User extends BaseEntity {

    //@Size(min = 8, max = 32, message = "username length should be between 8 and 32 characters")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    //@Size(min = 8, max = 32, message = "password length should be between 8 and 32 characters")
    @Column(name = "password", nullable = false)
    private String password;

}
