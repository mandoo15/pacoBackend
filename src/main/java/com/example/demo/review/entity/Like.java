package com.example.demo.review.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "likes")
public class Like {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

  
    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    private Long targetId;

    public enum TargetType {
        REVIEW, PARKING
    }
}
