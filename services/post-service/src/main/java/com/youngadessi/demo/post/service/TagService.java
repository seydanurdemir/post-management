package com.youngadessi.demo.post.service;

import com.youngadessi.demo.post.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    Page<Tag> getAllTags(Pageable pageable);

    Page<Tag> getAllTags(Long post_id, Pageable pageable);

    Tag getTag(Long id);

    Tag saveTag(Tag tag);

    Tag saveTag(Long post_id, Tag tag);

    Tag updateTag(Long id, Tag tag);

    Tag updateTag(Long post_id, Long id, Tag tag);

    void deleteTag(Long id);

}