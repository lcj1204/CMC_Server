package com.sctk.cmc.controller.member.productionProgress;

import com.sctk.cmc.auth.domain.SecurityMemberDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.member.productionProgress.MemberProductionProgressService;
import com.sctk.cmc.service.member.productionProgress.dto.MemberProductionProgressGetDetailResponse;
import com.sctk.cmc.service.member.productionProgress.dto.MemberProductionProgressGetInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member Production Progress", description = "구매자용 제작중 상품 API Document")
public class MemberProductionProgressController {
    private final MemberProductionProgressService memberProductionProgressService;

    @Operation(summary = "제작중 상품 전체 간단 조회 API", description = "구매자가 모든 제작중 상품을 간단 조회할 때 사용합니다.")
    @GetMapping("/production-progress")
    public BaseResponse<List<MemberProductionProgressGetInfoResponse>> retrieveAllInfo(@AuthenticationPrincipal SecurityMemberDetails memberDetails) {

        Long memberId = memberDetails.getId();
        List<MemberProductionProgressGetInfoResponse> responses = memberProductionProgressService.retrieveAllInfo(memberId);

        return new BaseResponse<>(responses);
    }

    @Operation(summary = "제작중 상품 상세 조회 API", description = "구매자가 제작중인 상품을 상세 조회할 때 사용합니다.")
    @GetMapping("/production-progress/{productionProgressId}/detail")
    public BaseResponse<MemberProductionProgressGetDetailResponse> retrieveDetail(@AuthenticationPrincipal SecurityMemberDetails memberDetails,
                                                                                  @PathVariable("productionProgressId") Long productionProgressId) {

        Long memberId = memberDetails.getId();
        MemberProductionProgressGetDetailResponse responses = memberProductionProgressService.retrieveDetailById(memberId, productionProgressId);

        return new BaseResponse<>(responses);
    }
}
