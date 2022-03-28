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
}
