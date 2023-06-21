package com.sctk.cmc.service.member.productionProgress;

import com.sctk.cmc.service.member.productionProgress.dto.MemberProductionProgressGetDetailResponse;
import com.sctk.cmc.service.member.productionProgress.dto.MemberProductionProgressGetInfoResponse;

import java.util.List;

public interface MemberProductionProgressService {

    List<MemberProductionProgressGetInfoResponse> retrieveAllInfo(Long memberId);

    MemberProductionProgressGetDetailResponse retrieveDetailById(Long memberId, Long productionProgressId);
}
