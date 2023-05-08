package com.sctk.cmc.auth.dto;

import com.sctk.cmc.auth.domain.Token;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private Long Id;
    private String accessToken;
    private String refreshToken;

    public static LoginResponseDto of(Long id, Token token) {
        return LoginResponseDto.builder()
                .Id(id)
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
