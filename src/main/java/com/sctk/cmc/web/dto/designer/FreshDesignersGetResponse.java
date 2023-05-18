package com.sctk.cmc.web.dto.designer;

import com.sctk.cmc.service.dto.designer.FilteredDesignerInfo;
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
