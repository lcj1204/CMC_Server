package com.sctk.cmc.service.designer.productionProgress.dto;

import com.sctk.cmc.domain.ProductionProgress;
import com.sctk.cmc.domain.ProgressType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductionProgressGetInfoResponse {
    private Long productionProgressId;
    private String mainImg;
    private String title;
    private String category;
    private int price;
    private ProgressType status;

    public static ProductionProgressGetInfoResponse of(ProductionProgress productionProgress) {
        return ProductionProgressGetInfoResponse.builder()
                .productionProgressId(productionProgress.getId())
                .mainImg(productionProgress.getMainImg())
                .title(productionProgress.getTitle())
                .category(productionProgress.getCategory())
                .price(productionProgress.getPrice())
                .status(productionProgress.getStatus())
                .build();
    }

}
