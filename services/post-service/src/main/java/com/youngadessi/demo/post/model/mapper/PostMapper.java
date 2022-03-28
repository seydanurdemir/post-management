package com.youngadessi.demo.post.model.mapper;

import com.youngadessi.demo.post.model.entity.Post;
import com.youngadessi.demo.post.model.dto.PostDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {

    PostDTO toDto(Post entity);

    Post toEntity(PostDTO dto);

    List<PostDTO> toDtoList(List<Post> entityList);

    List<Post> toEntityList(List<PostDTO> dtoList);

    /*Page<PostDTO> toDtoPage(Page<Post> entityPage);

    Page<Post> toEntityPage(Page<PostDTO> dtoPage);*/

}
