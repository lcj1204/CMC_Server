package com.sctk.cmc.common.config;

import com.sctk.cmc.auth.filter.JwtFilter;
import com.sctk.cmc.auth.jwt.JwtProvider;
import com.sctk.cmc.auth.service.SecurityUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final SecurityUserDetailsService securityUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // REST API 서버이므로 csrf 보안 필요 X
                .httpBasic().disable() // API 통신을 통한 로그인을 이용할 것으로, http 기반 로그인 X
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 인증 정보 저장 X
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**").permitAll()

                .antMatchers("/api/v1/members/**/info/**").hasRole("DESIGNER")
                .antMatchers("/api/v1/members/**").hasRole("MEMBER")


                .antMatchers("/api/v1/designers/ranks/**").hasAnyRole("MEMBER", "DESIGNER")
                .antMatchers("/api/v1/designers/**/categories/**").hasAnyRole("MEMBER", "DESIGNER")
                .antMatchers("/api/v1/designers/**/info/**").hasAnyRole("MEMBER", "DESIGNER")
                .antMatchers("/api/v1/designers/**/profiles/**").hasAnyRole("MEMBER", "DESIGNER")
                .antMatchers("/api/v1/designers/**/search/**").hasAnyRole("MEMBER", "DESIGNER")
                .antMatchers("/api/v1/designers/**").hasRole("DESIGNER")

                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/custom/**").permitAll()
                .antMatchers("/api/v1/product/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(
                        new JwtFilter(jwtProvider, securityUserDetailsService)
                        , UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
