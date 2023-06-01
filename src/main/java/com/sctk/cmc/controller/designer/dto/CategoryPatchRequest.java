package com.sctk.cmc.controller.designer.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryPatchRequest {
    private List<CategoryParam> categoryParams;
}
