package com.youngadessi.demo.post.controller;

import com.youngadessi.demo.post.model.dto.CommentDTO;
import com.youngadessi.demo.post.model.dto.PostDTO;
import com.youngadessi.demo.post.model.dto.TagDTO;
import com.youngadessi.demo.post.model.entity.Comment;
import com.youngadessi.demo.post.model.entity.Post;
import com.youngadessi.demo.post.model.entity.Tag;
import com.youngadessi.demo.post.model.mapper.CommentMapper;
import com.youngadessi.demo.post.model.mapper.PostMapper;
import com.youngadessi.demo.post.model.mapper.TagMapper;
import com.youngadessi.demo.post.service.CommentService;
import com.youngadessi.demo.post.service.PostService;
import com.youngadessi.demo.post.service.TagService;
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
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private final CommentService commentService;

    private final TagService tagService;

    private static final PostMapper POST_MAPPER = Mappers.getMapper(PostMapper.class);

    private static final CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);

    private static final TagMapper TAG_MAPPER = Mappers.getMapper(TagMapper.class);

    /* -------------------------------------------------------------------------------------------------------------- */
    /* ------------------------------------------------     POST     ------------------------------------------------ */
    /* -------------------------------------------------------------------------------------------------------------- */

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(@PageableDefault(page = 0, size = 3) @SortDefault.SortDefaults({ @SortDefault(sort = "content", direction = Sort.Direction.ASC), @SortDefault(sort = "id", direction = Sort.Direction.ASC) }) Pageable pageable) {
        return new ResponseEntity<>(postService.getAllPosts(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable(value = "id") @Min(1) Long id) {
        return new ResponseEntity<>(POST_MAPPER.toDto(postService.getPost(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDTO> savePost(@Valid @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(POST_MAPPER.toDto(postService.savePost(POST_MAPPER.toEntity(postDTO))), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable(value = "id") @Min(1) Long id, @Valid @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(POST_MAPPER.toDto(postService.updatePost(id, POST_MAPPER.toEntity(postDTO))), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(value = "id") @Min(1) Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* ------------------------------------------------    COMMENT   ------------------------------------------------ */
    /* -------------------------------------------------------------------------------------------------------------- */

    @GetMapping(value = "/{post_id}/comment")
    public ResponseEntity<Page<Comment>> getAllComments(@PathVariable(value = "post_id") @Min(1) Long post_id, @PageableDefault(page = 0, size = 3)  Pageable pageable) {
        return new ResponseEntity<>(commentService.getAllComments(post_id, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{post_id}/comment/search")
    public ResponseEntity<Page<Comment>> searchComments(@PathVariable(value = "post_id") @Min(1) Long post_id, @RequestParam String keyword, @PageableDefault(page = 0, size = 3)  Pageable pageable) {
        return new ResponseEntity<>(commentService.searchComments(post_id, keyword, pageable), HttpStatus.OK);
    }

    @PostMapping(value = "/{post_id}/comment")
    public ResponseEntity<CommentDTO> saveComment(@PathVariable(value = "post_id") @Min(1) Long post_id, @Valid @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(COMMENT_MAPPER.toDto(commentService.saveComment(post_id, COMMENT_MAPPER.toEntity(commentDTO))), HttpStatus.OK);
    }

    @PutMapping(value = "/{post_id}/comment/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "post_id") @Min(1) Long post_id, @PathVariable(value = "id") @Min(1) Long id, @Valid @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(COMMENT_MAPPER.toDto(commentService.updateComment(id, COMMENT_MAPPER.toEntity(commentDTO))), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{post_id}/comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(value = "post_id") @Min(1) Long post_id, @PathVariable(value = "id") @Min(1) Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* ------------------------------------------------      TAG     ------------------------------------------------ */
    /* -------------------------------------------------------------------------------------------------------------- */

    @GetMapping(value = "/{post_id}/tag")
    public ResponseEntity<Page<Tag>> getAllTags(@PathVariable(value = "post_id") @Min(1) Long post_id, @PageableDefault(page = 0, size = 3)  Pageable pageable) {
        return new ResponseEntity<>(tagService.getAllTags(post_id, pageable), HttpStatus.OK);
    }

    @PostMapping(value = "/{post_id}/tag/{tag_id}")
    public ResponseEntity<Tag> saveTag(@PathVariable(value = "post_id") @Min(1) Long post_id, @PathVariable(value = "tag_id") @Min(1) Long tag_id) {
        return new ResponseEntity<>(tagService.saveTag(post_id, tag_id), HttpStatus.OK);
    }

    @PostMapping(value = "/{post_id}/tag")
    public ResponseEntity<List<Tag>> saveTag(@PathVariable(value = "post_id") @Min(1) Long post_id, @RequestBody List<Long> tag_ids) {
        return new ResponseEntity<>(tagService.saveTag(post_id, tag_ids), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{post_id}/tag/{tag_id}")
    public ResponseEntity<Tag> deleteTag(@PathVariable(value = "post_id") @Min(1) Long post_id, @PathVariable(value = "tag_id") @Min(1) Long tag_id) {
        tagService.deleteTag(post_id, tag_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{post_id}/tag")
    public ResponseEntity<List<Tag>> deleteTag(@PathVariable(value = "post_id") @Min(1) Long post_id, @RequestBody List<Long> tag_ids) {
        tagService.deleteTag(post_id, tag_ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
