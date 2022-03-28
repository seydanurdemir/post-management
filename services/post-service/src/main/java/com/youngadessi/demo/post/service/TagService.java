package com.youngadessi.demo.post.service;

import com.youngadessi.demo.post.model.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getAllTags();

    Tag getTag(Long id);

    void addTag(Tag tag);

    Tag updateTag(Tag tag);

    boolean deleteTag(Long id);

}
