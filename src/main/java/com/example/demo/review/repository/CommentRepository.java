package com.example.demo.review.repository;

import com.example.demo.review.entity.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<ReviewComment, Long> {
    List<ReviewComment> findByReviewId(Long reviewId);
}
