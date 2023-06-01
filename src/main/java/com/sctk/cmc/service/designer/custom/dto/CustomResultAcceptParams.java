package com.sctk.cmc.service.designer.custom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
public class CustomResultAcceptParams {
    @NotNull(message = "[request] 예상 시작 날짜를 입력해주세요.")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate expectStartDate;

    @NotNull(message = "[request] 예상 마감 날짜를 입력해주세요.")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate expectEndDate;

    @NotNull(message = "[request] 확정된 가격을 입력해 주세요")
    @Positive
    private int expectPrice;

    private String message;
}
