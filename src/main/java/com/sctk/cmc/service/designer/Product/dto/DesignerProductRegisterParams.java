package com.sctk.cmc.service.designer.Product.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
public class DesignerProductRegisterParams {
    @NotBlank(message = "[request] 상품명을 입력해 주세요.")
    @Size(min = 2, max = 20, message = "[request] 상품명은 2 - 20글자로 입력해 주세요.")
    private String name;

    @NotBlank(message = "[request] 대분류를 입력해 주세요")
    private String highCategory;

    @NotBlank(message = "[request] 소분류를 입력해 주세요")
    private String lowCategory;

    @NotNull(message = "[request] 상품 가격을 입력해 주세요")
    @Positive // 양수만 가능
    private int price;

    private String tag;

    private String description;
}
