package com.youngadessi.demo.post.service;

import com.youngadessi.demo.post.model.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    Post getPost(Long id);

    void addPost(Post post);

    Post updatePost(Post post);

    boolean deletePost(Long id);

}
