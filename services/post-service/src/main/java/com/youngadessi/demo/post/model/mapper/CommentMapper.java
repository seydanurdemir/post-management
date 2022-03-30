package com.youngadessi.demo.post.model.mapper;

import com.youngadessi.demo.post.model.dto.CommentDTO;
import com.youngadessi.demo.post.model.entity.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    CommentDTO toDto(Comment entity);

    Comment toEntity(CommentDTO dto);

    List<CommentDTO> toDtoList(List<Comment> entityList);

    List<Comment> toEntityList(List<CommentDTO> dtoList);

}
