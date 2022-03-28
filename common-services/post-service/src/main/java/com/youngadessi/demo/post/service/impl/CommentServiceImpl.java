package com.youngadessi.demo.post.service.impl;

import com.youngadessi.demo.post.model.entity.Comment;
import com.youngadessi.demo.post.repository.CommentRepository;
import com.youngadessi.demo.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getComment(Long id) {
        Optional<Comment> byId = commentRepository.findById(id);
        return byId.orElseThrow(() -> new RuntimeException("Comment not found!"));
    }

    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public boolean deleteComment(Long id) {
        commentRepository.delete(getComment(id));
        return true;
    }

}
