package com.youngadessi.demo.post.controller;

import com.youngadessi.demo.post.model.dto.CommentDTO;
import com.youngadessi.demo.post.model.dto.PostDTO;
import com.youngadessi.demo.post.model.entity.Comment;
import com.youngadessi.demo.post.model.entity.Post;
import com.youngadessi.demo.post.model.mapper.CommentMapper;
import com.youngadessi.demo.post.model.mapper.PostMapper;
import com.youngadessi.demo.post.service.CommentService;
import com.youngadessi.demo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    private final CommentService commentService;

    private static final PostMapper POST_MAPPER = Mappers.getMapper(PostMapper.class);

    private static final CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(@PageableDefault(page = 0, size = 3) @SortDefault.SortDefaults({ @SortDefault(sort = "name", direction = Sort.Direction.DESC), @SortDefault(sort = "id", direction = Sort.Direction.ASC) }) Pageable pageable) {
        ResponseEntity<Page<Post>> response;

        Page<Post> allPosts = postService.getAllPosts(pageable);

        if (allPosts != null && !allPosts.isEmpty()) {
            response = new ResponseEntity<>(allPosts, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            // throw new NotFoundException("Posts");
        }

        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable(value = "id") @Min(1) Long id) {
        ResponseEntity<PostDTO> response;

        Post post = postService.getPost(id);

        if (post != null) {
            response = new ResponseEntity<>(POST_MAPPER.toDto(post), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PostMapping
    public ResponseEntity<PostDTO> savePost(@Valid @RequestBody PostDTO postDTO) {
        ResponseEntity<PostDTO> response;

        if (postDTO != null) {
            response = new ResponseEntity<>(POST_MAPPER.toDto(postService.savePost(POST_MAPPER.toEntity(postDTO))), HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable(value = "id") @Min(1) Long id, @Valid @RequestBody PostDTO postDTO) {
        ResponseEntity<PostDTO> response;

        if (id != null && postService.getPost(id) != null) {
            response = new ResponseEntity<>(POST_MAPPER.toDto(postService.updatePost(id, POST_MAPPER.toEntity(postDTO))), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(value = "id") @Min(1) Long id) {
        ResponseEntity<Void> response;

        if (postService.getPost(id) != null) {
            postService.deletePost(id);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping(value = "/{post_id}/comment")
    public ResponseEntity<Page<Comment>> getAllComments(@PathVariable(value = "post_id") @Min(1) Long post_id, @PageableDefault(page = 0, size = 3)  Pageable pageable) {
        ResponseEntity<Page<Comment>> response;

        Page<Comment> allComments = commentService.getAllComments(post_id, pageable);

        if (allComments != null && !allComments.isEmpty()) {
            response = new ResponseEntity<>(allComments, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping(value = "/{post_id}/comment/search")
    public ResponseEntity<Page<Comment>> searchComments(@PathVariable(value = "post_id") @Min(1) Long post_id, @RequestParam String keyword, @PageableDefault(page = 0, size = 3)  Pageable pageable) {
        ResponseEntity<Page<Comment>> response;

        Page<Comment> searchResult = commentService.searchComments(post_id, keyword, pageable);

        if (searchResult != null && !searchResult.isEmpty()) {
            response = new ResponseEntity<>(searchResult, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PostMapping(value = "/{post_id}/comment")
    public ResponseEntity<CommentDTO> saveComment(@PathVariable(value = "post_id") @Min(1) Long post_id, @Valid @RequestBody CommentDTO commentDTO) {
        ResponseEntity<CommentDTO> response;

        if (postService.getPost(post_id) != null) {
            response = new ResponseEntity<>(COMMENT_MAPPER.toDto(commentService.saveComment(post_id, COMMENT_MAPPER.toEntity(commentDTO))), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PutMapping(value = "/{post_id}/comment/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "post_id") @Min(1) Long post_id, @PathVariable(value = "id") @Min(1) Long id, @Valid @RequestBody CommentDTO commentDTO) {
        ResponseEntity<CommentDTO> response;

        if (postService.getPost(post_id) != null && commentService.getComment(id) != null) {
            response = new ResponseEntity<>(COMMENT_MAPPER.toDto(commentService.updateComment(post_id, id, COMMENT_MAPPER.toEntity(commentDTO))), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @DeleteMapping(value = "/{post_id}/comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(value = "post_id") @Min(1) Long post_id, @PathVariable(value = "id") @Min(1) Long id) {
        ResponseEntity<Void> response;

        if (postService.getPost(id) != null && commentService.getComment(id) != null) {
            commentService.deleteComment(id);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }
}
