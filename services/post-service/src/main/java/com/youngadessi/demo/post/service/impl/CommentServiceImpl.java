package com.youngadessi.demo.post.service.impl;

import com.youngadessi.demo.post.exception.InvalidRequestException;
import com.youngadessi.demo.post.exception.NotFoundException;
import com.youngadessi.demo.post.model.entity.Comment;
import com.youngadessi.demo.post.model.entity.Post;
import com.youngadessi.demo.post.repository.CommentRepository;
import com.youngadessi.demo.post.repository.PostRepository;
import com.youngadessi.demo.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Override
    public Page<Comment> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Comment getComment(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment saveComment(Comment comment) {
        if (comment.getCommentText() != null) {
            commentRepository.save(comment);

            return comment;
        } else {
            throw new InvalidRequestException("Comment");
        }
    }

    @Override
    public Comment updateComment(Long id, Comment comment) {
        Comment comment_ = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment"));

        if (comment.getCommentText() != null) {
            comment_.setCommentText(comment.getCommentText());

            commentRepository.save(comment_);

            return comment;
        } else {
            throw new InvalidRequestException("Comment");
        }
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.delete(getComment(id));
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* ------------------------------------------------ POST-COMMENT ------------------------------------------------ */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public Page<Comment> getAllComments(Long post_id, Pageable pageable) {
        Post post_ = postRepository.findById(post_id).orElseThrow(() -> new NotFoundException("Post"));
        List<Comment> allComments = post_.getPostComments();

        //Page<Comment> commentPage = new PageImpl<>(allComments, pageable, allComments.size());
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), allComments.size());
        final Page<Comment> commentPage2 = new PageImpl<>(allComments.subList(start, end), pageable, allComments.size());

        return commentPage2;
    }

    @Override
    public Page<Comment> searchComments(Long post_id, String keyword, Pageable pageable) {
        Post post_ = postRepository.findById(post_id).orElseThrow(() -> new NotFoundException("Post"));
        List<Comment> allComments = post_.getPostComments();
        List<Comment> searchedComments = allComments.stream().filter(comm -> comm.getCommentText().contains(keyword)).collect(Collectors.toList());

        //Page<Comment> commentPage = new PageImpl<>(commentList, pageable, commentList.size());
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), searchedComments.size());
        final Page<Comment> commentPage2 = new PageImpl<>(searchedComments.subList(start, end), pageable, searchedComments.size());

        return commentPage2;
    }

    @Override
    public Comment saveComment(Long post_id, Comment comment) {
        Post post_ = postRepository.findById(post_id).orElseThrow(() -> new NotFoundException("Post"));

        if (comment.getCommentText() != null) {
            comment.setPost(post_);

            commentRepository.save(comment);

            return comment;
        } else {
            throw new InvalidRequestException("Comment");
        }
    }

}
