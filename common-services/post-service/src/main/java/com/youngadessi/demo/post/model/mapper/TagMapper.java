package com.youngadessi.demo.post.model.mapper;

import com.youngadessi.demo.post.model.dto.TagDTO;
import com.youngadessi.demo.post.model.entity.Tag;
import org.mapstruct.Mapper;

@Mapper
public interface TagMapper {

    TagDTO toDto(Tag entity);

    Tag toEntity(TagDTO dto);

}
