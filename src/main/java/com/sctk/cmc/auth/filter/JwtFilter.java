package com.sctk.cmc.auth.filter;

import com.sctk.cmc.auth.dto.CommonUser;
import com.sctk.cmc.auth.jwt.JwtProvider;
import com.sctk.cmc.auth.service.SecurityUserDetailsService;
import com.sctk.cmc.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final SecurityUserDetailsService securityUserDetailsService;

    // 회원가입 외 모든 요청을 막아 놨는데, 얘가 문이 될거임. 여기서 권한을 부여
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("== JwtAuthenticationFilter ==");
        String token = jwtProvider.resolveToken(request);
        if (token != null && jwtProvider.isValidToken(token)) {
            CommonUser user = jwtProvider.getPayload(token);

            UserDetails userDetail;
            if (user.getRole().equals(Role.MEMBER.getRoleName())) {
                userDetail = securityUserDetailsService.loadMemberByUserEmail(user.getEmail());
            }
            else {
                userDetail = securityUserDetailsService.loadDesignerByUserEmail(user.getEmail());
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication( usernamePasswordAuthenticationToken );
        }
        filterChain.doFilter(request, response);
    }
}
