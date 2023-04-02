package com.sctk.cmc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // REST API 서버이므로 csrf 보안 필요 X
                .httpBasic().disable() // API 통신을 통한 로그인을 이용할 것으로, http 기반 로그인 X
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 인증 정보 저장 X
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest()
                .authenticated();

        return http.build();
    }
}
