package com.sctk.cmc.service.designer.productionProgress.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DesignerProductionProgressIdResponse {
    private Long productionProgressId;

    public static DesignerProductionProgressIdResponse of(Long productionProgressId) {
        return DesignerProductionProgressIdResponse.builder()
                .productionProgressId(productionProgressId)
                .build();
    }
}
