package com.vinca.backboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 스프링 시큐리티 핵심 파일
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //  htttp://localhost:8080/** 로그인 하지않고도 접근할 수 있는 권한을 주겠다 */
        http
            .authorizeHttpRequests((atr) -> atr.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
            // CSR 위변조 공격을 막는 부분 해제, 특정 URL은 csrf 공격 리스트에서 제거
            .csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
            // h2-console 페이지가 frameset, frame으로 구성 CORS와 유사한 옵션 추가
            .headers((headers) -> headers
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN  // ignoringRequestMatchers 영역에 있는 프레임 해제 요청
                )))
        ;
        return http.build();
    }
}
