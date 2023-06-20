package com.sctk.cmc.service.designer.productionProgress.dto;

import com.sctk.cmc.domain.ProgressType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ProductionProgressGetDetailResponse {
    private ProductionProgressGetInfoResponse productionProgressInfo;
    private Map<ProgressType, String> productionProgressImgMap;

    public static ProductionProgressGetDetailResponse of(ProductionProgressGetInfoResponse productionProgressInfo,
                                                         Map<ProgressType, String> productionProgressImgList) {
        return ProductionProgressGetDetailResponse.builder()
                .productionProgressInfo(productionProgressInfo)
                .productionProgressImgMap(productionProgressImgList)
                .build();
    }
}
