package com.sctk.cmc.controller.designer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DesignerJoinResponseDto {
    private Long id;

    public static DesignerJoinResponseDto of(Long memberId) {
        return DesignerJoinResponseDto.builder()
                .id(memberId)
                .build();
    }
}
