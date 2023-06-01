package com.sctk.cmc.controller.designer.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryPostRequest {
    private List<CategoryParam> categoryParams;
}
