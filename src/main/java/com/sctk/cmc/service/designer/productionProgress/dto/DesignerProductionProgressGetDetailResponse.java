package com.sctk.cmc.service.designer.productionProgress.dto;

import com.sctk.cmc.domain.ProgressType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class DesignerProductionProgressGetDetailResponse {
    private DesignerProductionProgressGetInfoResponse productionProgressInfo;
    private Map<ProgressType, List<String>> productionProgressImgMap;

    public static DesignerProductionProgressGetDetailResponse of(DesignerProductionProgressGetInfoResponse productionProgressInfo,
                                                                 Map<ProgressType, List<String>> productionProgressImgList) {
        return DesignerProductionProgressGetDetailResponse.builder()
                .productionProgressInfo(productionProgressInfo)
                .productionProgressImgMap(productionProgressImgList)
                .build();
    }
}
