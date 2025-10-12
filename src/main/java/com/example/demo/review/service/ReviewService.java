package com.example.demo.review.service;

import com.example.demo.review.dto.ReviewDto;
import com.example.demo.review.entity.Review;
import com.example.demo.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

   
    public ReviewDto createReview(ReviewDto dto) {
        Review review = Review.builder()
                .parkingId(dto.getParkingId())
                .userId(dto.getUserId())
                .content(dto.getContent())
                .parentId(dto.getParentId()) 
                .build();
        Review saved = reviewRepository.save(review);
        return toDto(saved);
    }

 
    public List<ReviewDto> getReviewsByParkingId(Long parkingId) {
        return reviewRepository.findByParkingIdAndParentIdIsNull(parkingId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

   
    public List<ReviewDto> getReplies(Long parentId) {
        return reviewRepository.findByParentId(parentId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

   
    public ReviewDto getReview(Long id) {
        return reviewRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

 
    public ReviewDto updateReview(ReviewDto dto) {
        return reviewRepository.findById(dto.getId())
                .map(review -> {
                    review.setContent(dto.getContent());
                    Review updated = reviewRepository.save(review);
                    return toDto(updated);
                })
                .orElse(null);
    }

   
    public void deleteReview(Long id) {
        
        List<Review> replies = reviewRepository.findByParentId(id);
        reviewRepository.deleteAll(replies);
        reviewRepository.deleteById(id);
    }

    
    private ReviewDto toDto(Review r) {
        return ReviewDto.builder()
                .id(r.getId())
                .parkingId(r.getParkingId())
                .userId(r.getUserId())
                .content(r.getContent())
                .parentId(r.getParentId())
                .createdAt(r.getCreatedAt())
                .build();
    }
}
