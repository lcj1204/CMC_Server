package com.sctk.cmc.auth.jwt;

import com.sctk.cmc.auth.domain.Token;
import com.sctk.cmc.auth.dto.CommonUser;
import com.sctk.cmc.auth.dto.TokenReissueRequestDto;
import com.sctk.cmc.auth.dto.TokenReissueResponseDto;
import com.sctk.cmc.util.redis.domain.RedisDesigner;
import com.sctk.cmc.util.redis.domain.RedisMember;
import com.sctk.cmc.util.redis.service.RedisService;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.common.exception.ResponseStatus;
import com.sctk.cmc.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sctk.cmc.common.exception.ResponseStatus.INCONSISTENCY_REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final JwtProvider jwtProvider;
    private final RedisService redisService;


    public TokenReissueResponseDto reissue(TokenReissueRequestDto requestDto) {
        String oldAccessToken = requestDto.getAccessToken();
        String oldRefreshToken = requestDto.getRefreshToken();
        CommonUser user = jwtProvider.getPayload(oldAccessToken);

        if (!validateRefreshToken(user, oldRefreshToken)) {
            throw new CMCException(INCONSISTENCY_REFRESH_TOKEN);
        }

        Token newToken = jwtProvider.generateToken(user);

        if (user.getRole().equals(Role.MEMBER.getRoleName())) {
            redisService.saveMember(user.getEmail(), newToken.getRefreshToken());
        }
        else if (user.getRole().equals(Role.DESIGNER.getRoleName())) {
            redisService.saveDesigner(user.getEmail(), newToken.getRefreshToken());
        }

        return TokenReissueResponseDto.of(newToken);
    }

    private boolean validateRefreshToken(CommonUser user, String oldRefreshToken) {
        if (user.getRole().equals(Role.MEMBER.getRoleName())) {
            RedisMember redisMember = redisService.getMember(user.getEmail());
            String refreshTokenInRedis = redisMember.getRefreshToken();
            return oldRefreshToken.equals(refreshTokenInRedis);
        }
        else if (user.getRole().equals(Role.DESIGNER.getRoleName())) {
            RedisDesigner redisDesigner = redisService.getDesigner(user.getEmail());
            String refreshTokenInRedis = redisDesigner.getRefreshToken();
            return oldRefreshToken.equals(refreshTokenInRedis);
        }
        throw new CMCException(ResponseStatus.INVALID_ROLE);
    }
}