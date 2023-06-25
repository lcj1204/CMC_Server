package com.sctk.cmc.controller.common.product.dto;

import com.sctk.cmc.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductGetBySearchingResponse {
    private Long productId;
    private String name;
    private String mainImgUrl;
    private int productLikeCount;
    private Long designerId;
    private String designerName;
    private String profileImgUrl;

    public static ProductGetBySearchingResponse of(Product product) {
        return ProductGetBySearchingResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .mainImgUrl(product.getThumbnailImgList().get(0).getUrl())
                .productLikeCount(product.getLikeCount())
                .designerId(product.getDesigner().getId())
                .designerName(product.getDesigner().getName())
                .profileImgUrl(product.getDesigner().getProfileImgUrl())
                .build();
    }
}
