package com.sctk.cmc.common.dto.designer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryParams {
    private String highCategoryName;
    private List<String> lowCategoryNames;
}
