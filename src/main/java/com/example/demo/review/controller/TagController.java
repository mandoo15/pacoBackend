package com.example.demo.review.controller;

import com.example.demo.review.dto.TagDto;
import com.example.demo.review.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagDto> create(@RequestBody Map<String,String> body) {
        return ResponseEntity.ok(tagService.createTag(body.get("name")));
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> list() {
        return ResponseEntity.ok(tagService.getTags());
    }
}
