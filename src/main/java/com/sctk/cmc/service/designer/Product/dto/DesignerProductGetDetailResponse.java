package com.sctk.cmc.service.designer.Product.dto;

import com.sctk.cmc.domain.ProductStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class DesignerProductGetDetailResponse {
    private String name;
    private int price;
    private String tag;
    private String description;
    private LocalTime releaseAt;
    private int likeCount;
    private ProductStatus status;
}
