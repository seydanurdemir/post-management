package com.youngadessi.demo.auth.model.mapper;

import com.youngadessi.demo.auth.model.dto.UserDTO;
import com.youngadessi.demo.auth.model.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDTO toDto(User entity);

    User toEntity(UserDTO dto);

}
