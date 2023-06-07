package com.sctk.cmc.controller.member.custom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sctk.cmc.domain.CustomResult;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MemberCustomResultGetAcceptanceResponse {
    private Long customResultId;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate expectStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate expectEndDate;
    private Integer desiredPrice;
    private String message;

    public static MemberCustomResultGetAcceptanceResponse of(CustomResult customResult) {
        return MemberCustomResultGetAcceptanceResponse.builder()
                .customResultId(customResult.getId())
                .expectStartDate(customResult.getExpectStartDate())
                .expectEndDate(customResult.getExpectEndDate())
                .desiredPrice(customResult.getExpectPrice())
                .message(customResult.getMessage())
                .build();
    }
}
