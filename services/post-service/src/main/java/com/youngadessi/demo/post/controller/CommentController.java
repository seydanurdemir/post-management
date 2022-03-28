package com.youngadessi.demo.post.controller;

import com.youngadessi.demo.post.model.dto.CommentDTO;
import com.youngadessi.demo.post.model.entity.Comment;
import com.youngadessi.demo.post.model.mapper.CommentMapper;
import com.youngadessi.demo.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    private static final CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);

    @GetMapping
    public List<CommentDTO> getAllComments() {
        List<Comment> allComments = commentService.getAllComments();
        return allComments.stream().map(COMMENT_MAPPER::toDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public CommentDTO getComment(@PathVariable @Min(1) Long id) {
        return COMMENT_MAPPER.toDto(commentService.getComment(id));
    }

    @PostMapping
    public void saveComment(@Valid @RequestBody CommentDTO comment) {
        commentService.addComment(COMMENT_MAPPER.toEntity(comment));
    }

    @PutMapping
    public CommentDTO updateComment(@Valid @RequestBody Comment comment) {
        if (comment.getId() == null) {
            throw new RuntimeException("Comment id can not be null for update!");
        }
        return COMMENT_MAPPER.toDto(commentService.updateComment(comment));
    }

    @DeleteMapping
    public boolean deleteComment(@RequestParam @Min(1) Long id) {
        return commentService.deleteComment(id);
    }

}
