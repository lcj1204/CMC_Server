package com.sctk.cmc.service.designer.productionProgress.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductionProgressIdResponse {
    private Long productionProgressId;

    public static ProductionProgressIdResponse of(Long productionProgressId) {
        return ProductionProgressIdResponse.builder()
                .productionProgressId(productionProgressId)
                .build();
    }
}
