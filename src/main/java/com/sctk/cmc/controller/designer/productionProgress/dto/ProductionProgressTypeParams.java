package com.sctk.cmc.controller.designer.productionProgress.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ProductionProgressTypeParams {
    @NotBlank(message = "[request] 진행 타입을 입력해 주세요.")
    private String progressType;
}
