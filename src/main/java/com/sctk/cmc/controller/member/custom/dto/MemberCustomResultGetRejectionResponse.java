package com.sctk.cmc.controller.member.custom.dto;

import com.sctk.cmc.domain.CustomResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberCustomResultGetRejectionResponse {
    private Long customResultId;
    private String message;

    public static MemberCustomResultGetRejectionResponse of(CustomResult customResult) {
        return MemberCustomResultGetRejectionResponse.builder()
                .customResultId(customResult.getId())
                .message(customResult.getMessage())
                .build();
    }
}
