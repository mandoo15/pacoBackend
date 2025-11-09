package com.example.demo.user.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> userInfo = new HashMap<>();

        if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            if (kakaoAccount != null) {
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                userInfo.put("id", attributes.get("id"));
                userInfo.put("email", kakaoAccount.getOrDefault("email", "kakao_user@paco.com"));
                userInfo.put("nickname", profile != null ? profile.get("nickname") : "카카오사용자");
                userInfo.put("profile_image", profile != null ? profile.get("profile_image_url") : null);
            }
        }

        if ("naver".equals(registrationId)) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            userInfo.put("id", response.get("id"));
            userInfo.put("email", response.get("email"));
            userInfo.put("nickname", response.get("name"));
            userInfo.put("profile_image", response.get("profile_image"));
        }

        if ("google".equals(registrationId)) {
            userInfo.put("id", oAuth2User.getAttribute("sub"));
            userInfo.put("email", oAuth2User.getAttribute("email"));
            userInfo.put("nickname", oAuth2User.getAttribute("name"));
            userInfo.put("profile_image", oAuth2User.getAttribute("picture"));
        }

        // 통합된 구조로 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                userInfo,
                "email" // 주 식별자
        );
    }
}
