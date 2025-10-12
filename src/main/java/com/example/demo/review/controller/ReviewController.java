package com.example.demo.review.controller;

import com.example.demo.review.dto.ReviewDto;
import com.example.demo.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

   
    @PostMapping
    public ResponseEntity<ReviewDto> create(@RequestBody ReviewDto dto) {
        return ResponseEntity.ok(reviewService.createReview(dto));
    }

   
    @PostMapping("/{parentId}/replies")
    public ResponseEntity<ReviewDto> createReply(@PathVariable Long parentId, @RequestBody ReviewDto dto) {
        dto.setParentId(parentId);
        return ResponseEntity.ok(reviewService.createReview(dto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> update(@PathVariable Long id, @RequestBody ReviewDto dto) {
        dto.setId(id); 
        ReviewDto updated = reviewService.updateReview(dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

  
    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<List<ReviewDto>> listByParking(@PathVariable Long parkingId) {
        return ResponseEntity.ok(reviewService.getReviewsByParkingId(parkingId));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> get(@PathVariable Long id) {
        ReviewDto dto = reviewService.getReview(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

  
    @GetMapping("/{id}/replies")
    public ResponseEntity<List<ReviewDto>> listReplies(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReplies(id));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
