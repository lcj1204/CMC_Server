package com.sctk.cmc.controller.member.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeProductGetExistenceResponse {
    Boolean liked;

    public static LikeProductGetExistenceResponse of(Boolean liked) {
        return LikeProductGetExistenceResponse.builder()
                .liked(liked)
                .build();
    }
}
