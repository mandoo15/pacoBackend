package com.example.demo.review.repository;

import com.example.demo.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByParkingIdAndParentIdIsNull(Long parkingId); // 일반 댓글
    List<Review> findByParentId(Long parentId); // 대댓글 추가
}
