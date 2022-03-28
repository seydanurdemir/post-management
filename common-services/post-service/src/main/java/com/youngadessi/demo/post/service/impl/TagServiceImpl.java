package com.youngadessi.demo.post.service.impl;

import com.youngadessi.demo.post.model.entity.Tag;
import com.youngadessi.demo.post.repository.TagRepository;
import com.youngadessi.demo.post.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getTag(Long id) {
        Optional<Tag> byId = tagRepository.findById(id);
        return byId.orElseThrow(() -> new RuntimeException("Tag not found!"));
    }

    @Override
    public void addTag(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public boolean deleteTag(Long id) {
        tagRepository.delete(getTag(id));
        return true;
    }

}
