package com.sctk.cmc.auth;

import com.sctk.cmc.auth.domain.Token;
import com.sctk.cmc.auth.dto.CommonUser;
import com.sctk.cmc.auth.dto.LoginResponseDto;
import com.sctk.cmc.auth.jwt.JwtProvider;
import com.sctk.cmc.util.redis.service.RedisService;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.common.exception.ResponseStatus;
import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.service.designer.DesignerService;
import com.sctk.cmc.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final MemberService memberService;
    private final DesignerService designerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @Override
    public Member authenticateMember(String email, String password) {
        if (!memberService.existsByEmail(email)) {
            throw new CMCException(ResponseStatus.AUTHENTICATION_ILLEGAL_EMAIL);
        }

        Member member = memberService.retrieveByEmail(email);
        if (passwordEncoder.matches(password, member.getPassword())) {
            return member;
        }

        throw new CMCException(ResponseStatus.AUTHENTICATION_ILLEGAL_PASSWORD);
    }

    @Override
    public Designer authenticateDesigner(String email, String password) {
        if (!designerService.existsByEmail(email)) {
            throw new CMCException(ResponseStatus.AUTHENTICATION_ILLEGAL_EMAIL);
        }

        Designer designer = designerService.retrieveByEmail(email);
        if (passwordEncoder.matches(password, designer.getPassword())) {
            return designer;
        }

        throw new CMCException(ResponseStatus.AUTHENTICATION_ILLEGAL_PASSWORD);
    }

    public LoginResponseDto loginMember(String email, String password) {
        if (!memberService.existsByEmail(email)) {
            throw new CMCException(ResponseStatus.AUTHENTICATION_ILLEGAL_EMAIL);
        }

        Member member = memberService.retrieveByEmail(email);
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CMCException(ResponseStatus.AUTHENTICATION_ILLEGAL_PASSWORD);
        }

        CommonUser user = CommonUser.builder()
                .role(member.getRole().getRoleName())
                .email(member.getEmail())
                .build();

        Token token = jwtProvider.generateToken(user);

        redisService.saveMember(member.getEmail(), token.getRefreshToken());

        return LoginResponseDto.of(member.getId(), token);
    }

    public LoginResponseDto loginDesigner(String email, String password) {
        if (!designerService.existsByEmail(email)) {
            throw new CMCException(ResponseStatus.AUTHENTICATION_ILLEGAL_EMAIL);
        }

        Designer designer = designerService.retrieveByEmail(email);
        if (!passwordEncoder.matches(password, designer.getPassword())) {
            throw new CMCException(ResponseStatus.AUTHENTICATION_ILLEGAL_PASSWORD);
        }

        CommonUser user = CommonUser.builder()
                .role(designer.getRole().getRoleName())
                .email(designer.getEmail())
                .build();

        Token token = jwtProvider.generateToken(user);

        redisService.saveDesigner(designer.getEmail(), token.getRefreshToken());

        return LoginResponseDto.of(designer.getId(), token);
    }
}
