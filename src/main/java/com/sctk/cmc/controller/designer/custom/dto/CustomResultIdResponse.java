package com.sctk.cmc.controller.designer.custom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomResultIdResponse {
    private Long customResultId;

    public static CustomResultIdResponse of(Long customResultId) {
        return CustomResultIdResponse.builder()
                .customResultId(customResultId)
                .build();
    }
}
