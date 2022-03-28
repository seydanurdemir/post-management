package com.youngadessi.demo.post.controller;

import com.youngadessi.demo.post.model.dto.TagDTO;
import com.youngadessi.demo.post.model.entity.Tag;
import com.youngadessi.demo.post.model.mapper.TagMapper;
import com.youngadessi.demo.post.service.TagService;
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
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    private static final TagMapper TAG_MAPPER = Mappers.getMapper(TagMapper.class);

    @GetMapping
    public List<TagDTO> getAllTags() {
        List<Tag> allTags = tagService.getAllTags();
        return allTags.stream().map(TAG_MAPPER::toDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public TagDTO getTag(@PathVariable @Min(1) Long id) {
        return TAG_MAPPER.toDto(tagService.getTag(id));
    }

    @PostMapping
    public void saveTag(@Valid @RequestBody TagDTO tag) {
        tagService.addTag(TAG_MAPPER.toEntity(tag));
    }

    @PutMapping
    public TagDTO updateTag(@Valid @RequestBody Tag tag) {
        if (tag.getId() == null) {
            throw new RuntimeException("Tag id can not be null for update!");
        }
        return TAG_MAPPER.toDto(tagService.updateTag(tag));
    }

    @DeleteMapping
    public boolean deleteTag(@RequestParam @Min(1) Long id) {
        return tagService.deleteTag(id);
    }

}
