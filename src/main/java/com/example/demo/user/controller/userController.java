package com.example.demo.user.controller;

import com.example.demo.user.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class userController { // ✅ 클래스명 대문자로 수정

    private final JwtUtil jwtUtil;

    public userController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // ✅ JWT 쿠키에서 로그인 유저 정보 반환
    @GetMapping(value = "/me", produces = "application/json")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        try {
            String jwt = getCookieValue(request, "jwt");
            if (jwt == null || jwt.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("message", "쿠키에 JWT가 없습니다."));
            }

            Claims claims = jwtUtil.parseToken(jwt);
            String email = claims.getSubject();

            String nickname = getCookieValue(request, "nickname");
            String profile = getCookieValue(request, "profile");

            Map<String, Object> result = new HashMap<>();
            result.put("email", email);
            result.put("nickname", nickname != null ? nickname : "사용자");
            result.put("profile", profile != null ? profile : "");
            result.put("message", "로그인 유효");

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(Map.of("message", "유효하지 않은 토큰입니다."));
        }
    }


    private String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
