package com.example.demo.review.dto;

import com.example.demo.review.entity.Tag;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TagDto {
    private Long id;
    private String name;

    public static TagDto fromEntity(Tag t) {
        return TagDto.builder().id(t.getId()).name(t.getName()).build();
    }
}
