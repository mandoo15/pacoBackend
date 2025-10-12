package com.example.demo.security;

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

        Map<String, Object> userAttributes = new HashMap<>();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        if ("naver".equals(registrationId)) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            userAttributes.put("id", response.get("id"));
            userAttributes.put("name", response.get("name"));
            userAttributes.put("email", response.get("email"));
            userAttributes.put("picture", response.get("profile_image"));
            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                    userAttributes,
                    "id");
        }

        if ("kakao".equals(registrationId)) {
            Object kakaoAccountObj = attributes.get("kakao_account");

            if (kakaoAccountObj instanceof Map) {
                Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoAccountObj;
                Object profileObj = kakaoAccount.get("profile");

                if (profileObj instanceof Map) {
                    Map<String, Object> profile = (Map<String, Object>) profileObj;

                    String nickname = (String) profile.getOrDefault("nickname", "unknown");
                    String profileImage = (String) profile.getOrDefault("profile_image_url", null);

                    return new DefaultOAuth2User(
                            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                            Map.of(
                                    "id", attributes.get("id"),
                                    "nickname", nickname,
                                    "profile_image", profileImage),
                            "nickname");
                }
            }

            // fallback
            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                    Map.of("id", attributes.get("id"), "nickname", "unknown"),
                    "nickname");
        }

        return oAuth2User;
    }
}
