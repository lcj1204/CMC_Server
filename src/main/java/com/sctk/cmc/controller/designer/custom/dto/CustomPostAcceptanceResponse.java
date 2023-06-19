package com.sctk.cmc.controller.designer.custom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomPostAcceptanceResponse {
    private Long customId;
    private Long productionProgressId;

    public static CustomPostAcceptanceResponse of( Long customId, Long productionProgressId ) {
        return CustomPostAcceptanceResponse.builder()
                .customId( customId )
                .productionProgressId( productionProgressId )
                .build();
    }
}
