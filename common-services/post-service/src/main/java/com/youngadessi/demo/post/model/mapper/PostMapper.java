package com.youngadessi.demo.post.model.mapper;

import com.youngadessi.demo.post.model.entity.Post;
import com.youngadessi.demo.post.model.dto.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface PostMapper {

    PostDTO toDto(Post entity);

    Post toEntity(PostDTO dto);

    List<PostDTO> toPostDTOList(List<Post> postList);

}
