package com.sctk.cmc.controller.designer.dto;

import com.sctk.cmc.service.designer.dto.FilteredDesignerInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PopularDesignersGetResponse {
    private List<FilteredDesignerInfo> popularDesigners;
}
