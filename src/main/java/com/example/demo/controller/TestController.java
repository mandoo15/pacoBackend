package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/protected")
    public String protectedApi() {
        return "이 API는 JWT가 있어야 접근 가능함.";
    }

    @GetMapping("/login-success")
    public String loginSuccessTest() {
        return "소셜 로그인 성공 백엔드에서 확인 완료";
    }
}
