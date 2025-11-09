package com.example.demo.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Map;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        // 로그인 성공 시 사용자 정보 출력
        System.out.println("OAuth2 로그인 성공!");
        System.out.println("인증 정보: " + authentication.getPrincipal());

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();
        String registrationId = oauthToken.getAuthorizedClientRegistrationId(); // google, kakao

        if ("kakao".equals(registrationId)) {
            Map<String, Object> attributes = oauthUser.getAttributes();
            Long kakaoId = ((Number) attributes.get("id")).longValue();

            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            String nickname = (String) attributes.get("nickname");
            String profileImage = (String) attributes.get("profile_image_url");

            System.out.println("ID: " + kakaoId);
            System.out.println("닉네임: " + nickname);
            System.out.println("프로필 이미지 URL: " + profileImage);

        }

        // 프론트엔드 대신 백엔드의 테스트 API로 리디렉션함 http://localhost:3000/login-success
        response.sendRedirect("/api/login-success");

        // 요청 완료 표시
        clearAuthenticationAttributes(request);
    }
}
