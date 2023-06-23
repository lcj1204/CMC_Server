package com.sctk.cmc.controller.common.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductGetBySearchingResponse {
    private Long productId;
    private String name;
    private String mainImgUrl;
    private int productLikeCount;
    private Long designerId;
    private String profileImgUrl;
}
