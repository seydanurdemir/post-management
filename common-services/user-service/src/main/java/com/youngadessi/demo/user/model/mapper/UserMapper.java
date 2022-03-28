package com.youngadessi.demo.user.model.mapper;

import com.youngadessi.demo.user.model.dto.UserDTO;
import com.youngadessi.demo.user.model.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDTO toDto(User entity);

    User toEntity(UserDTO dto);

}
