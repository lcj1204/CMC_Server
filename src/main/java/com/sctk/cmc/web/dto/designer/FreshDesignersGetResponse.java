package com.sctk.cmc.web.dto.designer;

import com.sctk.cmc.service.dto.designer.FilteredDesignerInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FreshDesignersGetResponse {
    private List<FilteredDesignerInfo> infos;
}
