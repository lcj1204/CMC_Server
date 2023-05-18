package com.sctk.cmc.web.dto.designer;

import com.sctk.cmc.common.dto.designer.CategoryView;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryGetResponse {
    private List<CategoryView> categoryViews;
}
