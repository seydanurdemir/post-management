package com.youngadessi.demo.post.controller;

import com.youngadessi.demo.post.model.dto.PostDTO;
import com.youngadessi.demo.post.model.entity.Post;
import com.youngadessi.demo.post.model.mapper.PostMapper;
import com.youngadessi.demo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    private static final PostMapper POST_MAPPER = Mappers.getMapper(PostMapper.class);

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(@PageableDefault(value = 5) Pageable pageable) {
        ResponseEntity<Page<Post>> response = null;

        Page<Post> allPosts = postService.getAllPosts(pageable);

        if (allPosts != null && !allPosts.isEmpty()) {
            response = new ResponseEntity<Page<Post>>(allPosts, HttpStatus.OK);
        } else {
            response = new ResponseEntity<Page<Post>>(HttpStatus.NOT_FOUND);
            // throw new NotFoundException("Any post could not be found!");
        }

        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> getPost(@PathVariable(value = "id") @Min(1) Long id) {
        ResponseEntity<Post> response = null;

        Post post = postService.getPost(id);

        if (post != null) {
            response = new ResponseEntity<Post>(post, HttpStatus.OK);
        } else {
            response = new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
            // throw new NotFoundException("Post");
        }

        return response;
    }

    @PostMapping
    public ResponseEntity<Post> savePost(@Valid @RequestBody Post post) {
        ResponseEntity<Post> response = null;

        if (post != null) {
            postService.savePost(post);
            response = new ResponseEntity<Post>(post, HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<Post>(HttpStatus.BAD_REQUEST);
            // throw new NotValidDataException("Post"); ???
        }

        return response;
    }

    @PutMapping
    public ResponseEntity<Post> updatePost(@Valid @RequestBody Post post) {
        ResponseEntity<Post> response = null;

        if (post != null && postService.getPost(post.getId()) != null) {
            postService.updatePost(post);
            response = new ResponseEntity<Post>(post, HttpStatus.OK);
        } else {
            response = new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
            // throw new NotFoundException("Post");
        }

        return response;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(value = "id") @Min(1) Long id) {
        ResponseEntity<Void> response = null;

        if (postService.getPost(id) != null) {
            postService.deletePost(id);
            response = new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            // throw new NotFoundException("Post");
        }

        return response;
    }
}
