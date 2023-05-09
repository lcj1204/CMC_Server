package com.sctk.cmc.web.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberJoinResponseDto {
    private Long id;

    public static MemberJoinResponseDto of(Long memberId) {
        return MemberJoinResponseDto.builder()
                .id(memberId)
                .build();
    }
}
