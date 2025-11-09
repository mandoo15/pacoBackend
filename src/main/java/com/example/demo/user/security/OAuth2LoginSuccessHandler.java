package com.example.demo.user.security;

import com.example.demo.user.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    // ✅ 프론트엔드 redirect URL
    private static final String FRONT_REDIRECT_URL_LOCAL = "http://localhost:3000/oauth/redirect";
    private static final String FRONT_REDIRECT_URL_PROD = "http://localhost:3000/oauth/redirect"; // 배포 시 교체

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();
        String registrationId = oauthToken.getAuthorizedClientRegistrationId();

        Map<String, Object> attributes = oauthUser.getAttributes();
        String email = null;
        String nickname = null;
        String profileImage = null;

        // ✅ 플랫폼별 사용자 정보 추출
        if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            if (kakaoAccount != null) {
                email = (String) kakaoAccount.get("email");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                if (profile != null) {
                    nickname = (String) profile.get("nickname");
                    profileImage = (String) profile.get("profile_image_url");
                }
            }
        } else if ("naver".equals(registrationId)) {
            Map<String, Object> responseMap = (Map<String, Object>) attributes.get("response");
            email = (String) responseMap.get("email");
            nickname = (String) responseMap.get("name");
            profileImage = (String) responseMap.get("profile_image");
        } else if ("google".equals(registrationId)) {
            email = oauthUser.getAttribute("email");
            nickname = oauthUser.getAttribute("name");
            profileImage = oauthUser.getAttribute("picture");
        }

        if (email == null) email = registrationId + "_user@paco.com";
        if (nickname == null) nickname = "사용자";

        // ✅ JWT 생성
        String token = jwtUtil.generateToken(email);

        // ✅ 환경 감지 (로컬 or 배포)
        String origin = request.getHeader("Origin");
        boolean isLocal = (origin != null && origin.contains("localhost"));

        // ✅ 쿠키 옵션 자동 세팅
        boolean isSecure = !isLocal; // 로컬은 false, 배포는 true
        String sameSite = isLocal ? "Lax" : "None"; // 로컬은 Lax, 배포는 None

        // ✅ JWT HttpOnly 쿠키
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
                .path("/")
                .httpOnly(true)
                .secure(isSecure)
                .sameSite(sameSite)
                .maxAge(60 * 60)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        // ✅ 닉네임 쿠키 (JS 접근 가능)
        ResponseCookie nickCookie = ResponseCookie.from("nickname",
                        URLEncoder.encode(nickname, StandardCharsets.UTF_8))
                .path("/")
                .httpOnly(false)
                .secure(isSecure)
                .sameSite(sameSite)
                .maxAge(60 * 60)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, nickCookie.toString());

        // ✅ 프로필 쿠키 (JS 접근 가능)
        ResponseCookie profileCookie = ResponseCookie.from("profile",
                        profileImage != null ? URLEncoder.encode(profileImage, StandardCharsets.UTF_8) : "")
                .path("/")
                .httpOnly(false)
                .secure(isSecure)
                .sameSite(sameSite)
                .maxAge(60 * 60)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, profileCookie.toString());

        // ✅ 로그인 완료 → 프론트로 리다이렉트
        String redirectUrl = isLocal ? FRONT_REDIRECT_URL_LOCAL : FRONT_REDIRECT_URL_PROD;
        response.sendRedirect(redirectUrl);
    }
}
