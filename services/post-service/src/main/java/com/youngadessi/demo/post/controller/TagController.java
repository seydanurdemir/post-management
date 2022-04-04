package com.youngadessi.demo.post.controller;

import com.youngadessi.demo.post.model.dto.TagDTO;
import com.youngadessi.demo.post.model.entity.Tag;
import com.youngadessi.demo.post.model.mapper.TagMapper;
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

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    private static final TagMapper TAG_MAPPER = Mappers.getMapper(TagMapper.class);

    @GetMapping
    public ResponseEntity<Page<Tag>> getAllTags(@PageableDefault(page = 0, size = 3) @SortDefault.SortDefaults({ @SortDefault(sort = "tagName", direction = Sort.Direction.ASC), @SortDefault(sort = "id", direction = Sort.Direction.ASC) }) Pageable pageable) {
        return new ResponseEntity<>(tagService.getAllTags(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TagDTO> getTag(@PathVariable(value = "id") @Min(1) Long id) {
        return new ResponseEntity<>(TAG_MAPPER.toDto(tagService.getTag(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TagDTO> saveTag(@Valid @RequestBody TagDTO tagDTO) {
        return new ResponseEntity<>(TAG_MAPPER.toDto(tagService.saveTag(TAG_MAPPER.toEntity(tagDTO))), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable(value = "id") @Min(1) Long id, @Valid @RequestBody TagDTO tagDTO) {
        return new ResponseEntity<>(TAG_MAPPER.toDto(tagService.updateTag(id, TAG_MAPPER.toEntity(tagDTO))), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable(value = "id") @Min(1) Long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
