package com.sctk.cmc.controller.member.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberProductLikeGetResponse {
    Boolean liked;

    public static MemberProductLikeGetResponse of(Boolean liked) {
        return MemberProductLikeGetResponse.builder()
                .liked(liked)
                .build();
    }
}
