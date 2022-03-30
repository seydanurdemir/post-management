package com.youngadessi.demo.post.service;

import com.youngadessi.demo.post.model.entity.Comment;
import com.youngadessi.demo.post.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Page<Post> getAllPosts(Pageable pageable);

    Post getPost(Long id);

    Post savePost(Post post);

    Post updatePost(Long id, Post post);

    void deletePost(Long id);

}
