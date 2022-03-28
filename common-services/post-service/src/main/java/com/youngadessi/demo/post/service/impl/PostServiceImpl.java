package com.youngadessi.demo.post.service.impl;

import com.youngadessi.demo.post.model.entity.Post;
import com.youngadessi.demo.post.repository.PostRepository;
import com.youngadessi.demo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPost(Long id) {
        Optional<Post> byId = postRepository.findById(id);
        return byId.orElseThrow(() -> new RuntimeException("Post not found!"));
    }

    @Override
    public void addPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        Post post_=postRepository.findById(post.getId()).orElse(null);

        post_.setCreatedByName("werwerwe");

        return postRepository.save(post_);
    }

    @Override
    public boolean deletePost(Long id) {
        postRepository.delete(getPost(id));
        return true;
    }

}
