package com.sctk.cmc.web.dto.designer;

import com.sctk.cmc.common.dto.designer.CategoryParams;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryPostRequest {
    private List<CategoryParams> categoryParams;
}
