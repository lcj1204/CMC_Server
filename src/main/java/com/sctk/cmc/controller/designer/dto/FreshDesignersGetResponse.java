package com.sctk.cmc.controller.designer.dto;

import com.sctk.cmc.service.designer.dto.FilteredDesignerInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class FreshDesignersGetResponse {
    private int count;
    private List<FilteredDesignerInfo> infos;

    public FreshDesignersGetResponse(List<FilteredDesignerInfo> infos) {
        this.count = infos.size();
        this.infos = infos;
    }
}
