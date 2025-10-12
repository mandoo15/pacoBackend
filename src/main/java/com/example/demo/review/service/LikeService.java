package com.example.demo.review.service;

import com.example.demo.review.entity.Like;
import com.example.demo.review.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;

    public boolean toggleLike(Long userId, Like.TargetType type, Long targetId) {
        Optional<Like> opt = likeRepository.findByUserIdAndTargetTypeAndTargetId(userId, type, targetId);
        if (opt.isPresent()) {
            likeRepository.delete(opt.get());
            return false;
        } else {
            Like l = Like.builder()
                    .userId(userId)
                    .targetType(type)
                    .targetId(targetId)
                    .build();
            likeRepository.save(l);
            return true;
        }
    }

    public long countLikes(Like.TargetType type, Long targetId) {
        return likeRepository.countByTargetTypeAndTargetId(type, targetId);
    }
}
