package com.example.demo.review.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private Long parkingId;
    private Long userId;
    private String content;
    private Long parentId;
    private LocalDateTime createdAt;
}
