package com.youngadessi.demo.post.service;

import com.youngadessi.demo.post.model.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();

    Comment getComment(Long id);

    void addComment(Comment comment);

    Comment updateComment(Comment comment);

    boolean deleteComment(Long id);

}
