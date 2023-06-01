package com.sctk.cmc.controller.designer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryParam {
    private String highCategoryName;
    private List<String> lowCategoryNames;
}
