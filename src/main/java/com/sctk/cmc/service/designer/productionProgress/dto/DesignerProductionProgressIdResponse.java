package com.sctk.cmc.service.designer.productionProgress.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DesignerProductionProgressIdResponse {
    private Long productionProgressId;
    private List<String> uploadUrls;

    public static DesignerProductionProgressIdResponse of(Long productionProgressId, List<String> uploadUrls) {
        return DesignerProductionProgressIdResponse.builder()
                .productionProgressId(productionProgressId)
                .uploadUrls(uploadUrls)
                .build();
    }
}
