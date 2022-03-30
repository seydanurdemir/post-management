package com.youngadessi.demo.post.service.impl;

import com.youngadessi.demo.post.model.entity.Comment;
import com.youngadessi.demo.post.model.entity.Post;
import com.youngadessi.demo.post.repository.CommentRepository;
import com.youngadessi.demo.post.repository.PostRepository;
import com.youngadessi.demo.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Override
    public Page<Comment> getAllComments(Long post_id, Pageable pageable) {
        Post post_ = postRepository.findById(post_id).orElse(null);
        List<Comment> commentList = post_.getPostComments();

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), commentList.size());
        final Page<Comment> commentPage2 = new PageImpl<>(commentList.subList(start, end), pageable, commentList.size());
        //Page<Comment> commentPage = new PageImpl<>(commentList, pageable, commentList.size());

        return commentPage2;
    }

    @Override
    public Page<Comment> searchComments(Long post_id, String keyword, Pageable pageable) {
        Post post_ = postRepository.findById(post_id).orElse(null);
        List<Comment> commentList = post_.getPostComments();
        List<Comment> searchedList = commentList.stream().filter(comm -> comm.getCommentText().contains(keyword)).collect(Collectors.toList());

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), searchedList.size());
        final Page<Comment> commentPage2 = new PageImpl<>(searchedList.subList(start, end), pageable, searchedList.size());
        //Page<Comment> commentPage = new PageImpl<>(commentList, pageable, commentList.size());

        return commentPage2;
    }

    @Override
    public Comment getComment(Long id) {
        Optional<Comment> byId = commentRepository.findById(id);
        return byId.orElseThrow(() -> new RuntimeException("Comment not found!"));
    }

    @Override
    public Comment saveComment(Long post_id, Comment comment) {
        Post post_ = postRepository.findById(post_id).orElse(null);

        comment.setPost(post_);
        commentRepository.save(comment);

        return comment;
    }

    @Override
    public Comment updateComment(Long post_id, Long id, Comment comment) {
        //Post post_ = postRepository.findById(post_id).orElse(null);
        Comment comment_ = commentRepository.findById(id).orElse(null);

        //comment_.setPost(post_);
        comment_.setCreatedByName(comment.getCreatedByName());
        comment_.setCommentText(comment.getCommentText());

        return commentRepository.save(comment_);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.delete(getComment(id));
    }

}
