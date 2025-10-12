package com.example.demo.review.controller;

import com.example.demo.review.dto.CommentDto;
import com.example.demo.review.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/review/{reviewId}")
    public ResponseEntity<CommentDto> create(@PathVariable Long reviewId, @RequestBody CommentDto dto) {
        return ResponseEntity.ok(commentService.createComment(reviewId, dto));
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<CommentDto>> listByReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(commentService.getCommentsByReview(reviewId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
