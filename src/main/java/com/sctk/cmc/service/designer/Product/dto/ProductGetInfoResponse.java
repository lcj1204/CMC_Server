package com.sctk.cmc.service.designer.Product.dto;

import com.sctk.cmc.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductGetInfoResponse {
    private Long productId;
    private String mainImgUrl;

    public static ProductGetInfoResponse of(Product product) {
        return ProductGetInfoResponse.builder()
                .productId(product.getId())
                .mainImgUrl(product.getImgs().get(0).getUrl())
                .build();
    }
}
