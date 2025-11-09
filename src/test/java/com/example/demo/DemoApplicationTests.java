package com.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.demo.user.security.CustomOAuth2UserService;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class DemoApplicationTests {

	@TestConfiguration
	static class TestConfig {
		@Bean
		public CustomOAuth2UserService customOAuth2UserService() {
			// Mockito 목(mock) 빈 수동 생성
			return Mockito.mock(CustomOAuth2UserService.class);
		}
	}

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Test
	void contextLoads() {
		// 테스트 기본 컨텍스트가 로딩되는지 확인
	}
}
