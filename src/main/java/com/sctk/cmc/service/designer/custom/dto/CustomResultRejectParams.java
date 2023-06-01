package com.sctk.cmc.service.designer.custom.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class CustomResultRejectParams {
    @NotBlank(message = "[request] 거절 사유를 입력해 주세요")
    @Size(max = 50, message = "[request] 50글자 이하로 입력해주세요.")
    private String message;
}
