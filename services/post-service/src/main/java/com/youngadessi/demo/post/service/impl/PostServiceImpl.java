package com.youngadessi.demo.post.service.impl;

import com.youngadessi.demo.post.exception.NotFoundException;
import com.youngadessi.demo.post.model.entity.Post;
import com.youngadessi.demo.post.repository.PostRepository;
import com.youngadessi.demo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post"));
    }

    @Override
    public Post savePost(Post post) {
        postRepository.save(post);

        return post;
    }

    @Override
    public Post updatePost(Long id, Post post) {
        Post post_ = postRepository.findById(id).orElse(null);

        post_.setCreatedByName(post.getCreatedByName());
        post_.setContent(post.getContent());

        return postRepository.save(post_);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.delete(getPost(id));
    }

}
