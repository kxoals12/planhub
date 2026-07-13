package com.planhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 차단 비활성화
            .cors(cors -> {})             // CORS 기본 허용
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 💡 모든 주소(학교 검색 포함) 요청을 로그인 없이 허용
            );
        
        return http.build();
    }
}