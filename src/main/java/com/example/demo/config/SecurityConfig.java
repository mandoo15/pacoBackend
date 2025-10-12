package com.example.demo.config;

import com.example.demo.security.CustomOAuth2UserService;
import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.OAuth2LoginSuccessHandler;
import com.example.demo.util.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtUtil jwtUtil;
        private final CustomOAuth2UserService customOAuth2UserService;
        private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

//        public SecurityConfig(JwtUtil jwtUtil,
//                              CustomOAuth2UserService customOAuth2UserService,
//                              OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) {
//                this.jwtUtil = jwtUtil;
//                this.customOAuth2UserService = customOAuth2UserService;
//                this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
//        }


        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // Spring Security CORS 설정
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .cors(Customizer.withDefaults())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/login", "/api/register", "/api/login-success")
                                                .permitAll() // 로그인, 회원가입은
                                                .requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll() // 소셜 로그인
                                                                                                               // 관련 URL
                                                                                                               // 허용
                                                .requestMatchers("/login-success").permitAll()
                                                .requestMatchers("/api/protected").authenticated() // 이 경로는 인증 필요
                                                .anyRequest().authenticated() // 나머지는 인증 필요
                                )
                                .oauth2Login(oauth2 -> oauth2
                                                .userInfoEndpoint(userInfo -> userInfo
                                                                .userService(customOAuth2UserService))
                                                .defaultSuccessUrl("/api/login-success", true)
                                                .successHandler(oAuth2LoginSuccessHandler))
                                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil),
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        // 실제 허용할 도메인 설정 (프론트 주소 - 현재는 localhost:3000)
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of(
                                "http://localhost:3000"// , // 개발 환경용
                // "https://frontend.com" // 실제 배포된 프론트 주소 이후에 설정 후 재배포
                ));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true); // 프론트에서 쿠키/세션 쓸 경우 필요

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }
}
