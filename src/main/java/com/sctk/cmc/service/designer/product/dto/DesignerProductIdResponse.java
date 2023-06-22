package com.sctk.cmc.service.designer.product.dto;

import com.sctk.cmc.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DesignerProductIdResponse {
    private Long productId;

    public static DesignerProductIdResponse of(Product product) {
        return DesignerProductIdResponse.builder()
                .productId(product.getId())
                .build();
    }
}
