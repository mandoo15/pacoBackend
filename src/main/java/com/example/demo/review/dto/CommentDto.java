package com.example.demo.review.dto;

import com.example.demo.review.entity.ReviewComment;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CommentDto {
    private Long id;
    private Long reviewId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    public static CommentDto fromEntity(ReviewComment c) {
        return CommentDto.builder()
                .id(c.getId())
                .reviewId(c.getReview().getId())
                .userId(c.getUserId())
                .content(c.getContent())
                .createdAt(c.getCreatedAt())
                .build();
    }
}
