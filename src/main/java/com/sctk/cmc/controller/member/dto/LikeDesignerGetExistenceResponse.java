package com.sctk.cmc.controller.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeDesignerGetExistenceResponse {
    private boolean liked;

    public static LikeDesignerGetExistenceResponse of(boolean liked) {
        return LikeDesignerGetExistenceResponse.builder()
                .liked(liked)
                .build();
    }
}
