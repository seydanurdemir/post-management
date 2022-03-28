package com.youngadessi.demo.post.model.mapper;

import com.youngadessi.demo.post.model.dto.CommentDTO;
import com.youngadessi.demo.post.model.entity.Comment;
import org.mapstruct.Mapper;

@Mapper
public interface CommentMapper {

    CommentDTO toDto(Comment entity);

    Comment toEntity(CommentDTO dto);

}
