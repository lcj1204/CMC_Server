package com.sctk.cmc.auth.dto;

import com.sctk.cmc.auth.domain.Token;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenReissueResponseDto {
    private String accessToken;
    private String refreshToken;

    public static TokenReissueResponseDto of( Token token ) {
        return TokenReissueResponseDto.builder()
                .accessToken( token.getAccessToken() )
                .refreshToken( token.getRefreshToken() )
                .build();
    }
}
