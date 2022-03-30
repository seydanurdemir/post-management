package com.youngadessi.demo.post.service.impl;

import com.youngadessi.demo.post.exception.NotFoundException;
import com.youngadessi.demo.post.model.entity.Comment;
import com.youngadessi.demo.post.model.entity.Post;
import com.youngadessi.demo.post.model.entity.Tag;
import com.youngadessi.demo.post.repository.PostRepository;
import com.youngadessi.demo.post.repository.TagRepository;
import com.youngadessi.demo.post.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final PostRepository postRepository;

    private final TagRepository tagRepository;

    @Override
    public Page<Tag> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Page<Tag> getAllTags(Long post_id, Pageable pageable) {
        Post post_ = postRepository.findById(post_id).orElse(null);
        List<Tag> allTags = post_.getPostTags();

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), allTags.size());
        final Page<Tag> tagPage2 = new PageImpl<>(allTags.subList(start, end), pageable, allTags.size());
        //Page<Tag> tagPage = new PageImpl<>(allTags, pageable, allTags.size());

        return tagPage2;
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Tag"));
    }

    @Override
    public Tag saveTag(Tag tag) {
        tagRepository.save(tag);

        return tag;
    }

    @Override
    public Tag saveTag(Long post_id, Tag tag) {
        tagRepository.save(tag);

        return tag;
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag_ = tagRepository.findById(id).orElse(null);

        tag_.setTagName(tag.getTagName());

        return tagRepository.save(tag_);
    }

    @Override
    public Tag updateTag(Long post_id, Long id, Tag tag) {
        Tag tag_ = tagRepository.findById(id).orElse(null);

        tag_.setTagName(tag.getTagName());

        return tagRepository.save(tag_);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(getTag(id));
    }

}
