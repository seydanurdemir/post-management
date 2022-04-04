package com.youngadessi.demo.post.service;

import com.youngadessi.demo.post.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Page<Tag> getAllTags(Pageable pageable);

    Tag getTag(Long id);

    Tag saveTag(Tag tag);

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);

    /* Post Related */

    Page<Tag> getAllTags(Long post_id, Pageable pageable);

    Tag saveTag(Long post_id, Long tag_id);

    List<Tag> saveTag(Long post_id, List<Long> tag_ids);

    void deleteTag(Long post_id, Long tag_id);

    void deleteTag(Long post_id, List<Long> tag_ids);

}