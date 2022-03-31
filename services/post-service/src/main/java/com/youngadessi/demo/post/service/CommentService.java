package com.youngadessi.demo.post.service;

import com.youngadessi.demo.post.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<Comment> getAllComments(Pageable pageable);

    Comment getComment(Long id);

    Comment saveComment(Comment comment);

    Comment updateComment(Long id, Comment comment);

    void deleteComment(Long id);

    /* Post Related */

    Page<Comment> getAllComments(Long post_id, Pageable pageable);

    Page<Comment> searchComments(Long post_id, String keyword, Pageable pageable);

    Comment saveComment(Long post_id, Comment comment);

}
