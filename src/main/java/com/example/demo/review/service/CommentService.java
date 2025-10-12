package com.example.demo.review.service;

import com.example.demo.review.dto.CommentDto;
import com.example.demo.review.entity.Review;
import com.example.demo.review.entity.ReviewComment;
import com.example.demo.review.repository.CommentRepository;
import com.example.demo.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

    public CommentDto createComment(Long reviewId, CommentDto dto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("no review"));
        ReviewComment c = ReviewComment.builder()
                .review(review)
                .userId(dto.getUserId())
                .content(dto.getContent())
                .build();
        ReviewComment saved = commentRepository.save(c);
        return CommentDto.fromEntity(saved);
    }

    public List<CommentDto> getCommentsByReview(Long reviewId) {
        return commentRepository.findByReviewId(reviewId).stream().map(CommentDto::fromEntity).collect(Collectors.toList());
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
