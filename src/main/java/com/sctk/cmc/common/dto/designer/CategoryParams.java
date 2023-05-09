package com.sctk.cmc.common.dto.designer;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryParams {
    private String highCategoryName;
    private List<String> lowCategoryNames;
}
