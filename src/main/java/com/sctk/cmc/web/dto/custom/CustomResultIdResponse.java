package com.sctk.cmc.web.dto.custom;

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
