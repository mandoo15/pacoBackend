package com.example.demo.review.controller;

import com.example.demo.review.entity.Like;
import com.example.demo.review.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/toggle")
    public ResponseEntity<Map<String,Object>> toggle(@RequestBody Map<String,Object> body) {
        Long userId = ((Number)body.get("userId")).longValue();
        String t = (String)body.get("targetType");
        Long targetId = ((Number)body.get("targetId")).longValue();
        Like.TargetType type = Like.TargetType.valueOf(t);
        boolean liked = likeService.toggleLike(userId, type, targetId);
        long count = likeService.countLikes(type, targetId);
        return ResponseEntity.ok(Map.of("liked", liked, "count", count));
    }
    
    @GetMapping("/count")
    public ResponseEntity<Map<String,Object>> count(@RequestParam String targetType, @RequestParam Long targetId) {
        Like.TargetType type = Like.TargetType.valueOf(targetType);
        long count = likeService.countLikes(type, targetId);
        return ResponseEntity.ok(Map.of("count", count));
    }
}
