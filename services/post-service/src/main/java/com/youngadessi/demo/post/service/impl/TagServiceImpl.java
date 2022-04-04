package com.youngadessi.demo.post.service.impl;

import com.youngadessi.demo.post.exception.InvalidRequestException;
import com.youngadessi.demo.post.exception.NotFoundException;
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
import java.util.stream.Collectors;

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
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag saveTag(Tag tag) {
        if (tag.getTagName() != null) {
            tagRepository.save(tag);

            return tag;
        } else {
            throw new InvalidRequestException("Tag");
        }
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag_ = tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Tag"));

        if (tag.getTagName() != null) {
            tag_.setTagName(tag.getTagName());

            tagRepository.save(tag_);

            return tag;
        } else {
            throw new InvalidRequestException("Tag");
        }
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Tag")));
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* ------------------------------------------------   POST-TAG   ------------------------------------------------ */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public Page<Tag> getAllTags(Long post_id, Pageable pageable) {
        Post post_ = postRepository.findById(post_id).orElseThrow(() -> new NotFoundException("Post"));
        List<Tag> allTags = post_.getPostTags();

        //Page<Tag> tagPage = new PageImpl<>(allTags, pageable, allTags.size());
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), allTags.size());
        final Page<Tag> tagPage2 = new PageImpl<>(allTags.subList(start, end), pageable, allTags.size());

        return tagPage2;
    }

    @Override
    public Tag saveTag(Long post_id, Long tag_id) {
        Post post_ = postRepository.findById(post_id).orElseThrow(() -> new NotFoundException("Post"));
        Tag tag_ = tagRepository.findById(tag_id).orElseThrow(() -> new NotFoundException("Tag"));
        List<Tag> postTags_ = post_.getPostTags();

        postTags_.add(tag_);

        post_.setPostTags(postTags_);
        postRepository.save(post_);

        return tag_;
    }

    @Override
    public List<Tag> saveTag(Long post_id, List<Long> tag_ids) {
        Post post_ = postRepository.findById(post_id).orElseThrow(() -> new NotFoundException("Post"));
        List<Tag> postTags_ = post_.getPostTags();
        List<Tag> tags = tag_ids.stream().map(x -> tagRepository.findById(x).orElse(null)).collect(Collectors.toList());

        postTags_.addAll(tags);

        post_.setPostTags(postTags_.stream().distinct().collect(Collectors.toList()));
        postRepository.save(post_);

        return tags;
    }

    @Override
    public void deleteTag(Long post_id, Long tag_id) {
        Post post_ = postRepository.findById(post_id).orElseThrow(() -> new NotFoundException("Post"));
        List<Tag> postTags_ = post_.getPostTags();

        postTags_.removeIf(x -> (x.getId() == tag_id));

        post_.setPostTags(postTags_);
        postRepository.save(post_);
    }

    @Override
    public void deleteTag(Long post_id, List<Long> tag_ids) {
        Post post_ = postRepository.findById(post_id).orElseThrow(() -> new NotFoundException("Post"));
        List<Tag> postTags_ = post_.getPostTags();

        tag_ids.forEach(x -> postTags_.removeIf(y -> (y.getId() == x)));

        post_.setPostTags(postTags_);
        postRepository.save(post_);
    }

}
