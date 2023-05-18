package com.sctk.cmc.common.dto.designer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryView {
    private String highCategoryName;
    private List<String> lowCategoryNames;
}
