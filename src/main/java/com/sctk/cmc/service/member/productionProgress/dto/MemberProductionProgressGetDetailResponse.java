package com.sctk.cmc.service.member.productionProgress.dto;

import com.sctk.cmc.domain.ProgressType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class MemberProductionProgressGetDetailResponse {
    private Long DDay;
    private MemberProductionProgressGetInfoResponse productionProgressInfo;
    private Map<ProgressType, List<String>> productionProgressImgMap;

    public static MemberProductionProgressGetDetailResponse of(Long DDay, MemberProductionProgressGetInfoResponse productionProgressInfo,
                                                               Map<ProgressType, List<String>> productionProgressImgList) {
        return MemberProductionProgressGetDetailResponse.builder()
                .DDay(DDay)
                .productionProgressInfo(productionProgressInfo)
                .productionProgressImgMap(productionProgressImgList)
                .build();
    }
}
