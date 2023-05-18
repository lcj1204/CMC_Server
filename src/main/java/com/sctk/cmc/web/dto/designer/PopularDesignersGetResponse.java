package com.sctk.cmc.web.dto.designer;

import com.sctk.cmc.service.dto.designer.PopularDesignerInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PopularDesignersGetResponse {
    private List<PopularDesignerInfo> popularDesigners;
}
