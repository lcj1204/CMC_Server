package com.sctk.cmc.service.dto.custom;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
@Builder
public class CustomRegisterParams {
    @NotBlank(message = "[request] 요청서 제목을 입력해 주세요.")
    @Size(min = 2, max = 20, message = "[request] 요청서 제목은 2 - 20글자로 입력해 주세요.")
    private String title;

    @NotBlank(message = "[request] 대분류를 입력해 주세요")
    private String highCategory;

    @NotBlank(message = "[request] 소분류를 입력해 주세요")
    private String lowCategory;

    @NotNull(message = "[request] 희망 가격을 입력해 주세요")
    @Positive // 양수만 가능
    private int desiredPrice;

    @Size(max = 50, message = "[request] 요청 사항은 최대 50글자 입니다.")
    private String requirement;

    // 이미지 빠짐

    @NotNull(message = "[request] 디자이너를 선택해 주세요.")
    @Positive
    private Long designerId;
}
