package com.example.demo.review.service;

import com.example.demo.review.dto.TagDto;
import com.example.demo.review.entity.Tag;
import com.example.demo.review.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    public TagDto createTag(String name) {
        Tag t = Tag.builder().name(name).build();
        return TagDto.fromEntity(tagRepository.save(t));
    }

    public List<TagDto> getTags() {
        return tagRepository.findAll().stream().map(TagDto::fromEntity).collect(Collectors.toList());
    }
}
