package com.sctk.cmc.controller.designer.custom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomIdResponse {
    private Long customId;

    public static CustomIdResponse of(Long customId) {
        return CustomIdResponse.builder()
                .customId(customId)
                .build();
    }
}
