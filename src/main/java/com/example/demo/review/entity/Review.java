package com.example.demo.review.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parkingId;
    private Long userId;
    private String content;
    private Long parentId;

    @Column(updatable = false)
    private java.time.LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "review_tag",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = java.time.LocalDateTime.now();
    }
}

