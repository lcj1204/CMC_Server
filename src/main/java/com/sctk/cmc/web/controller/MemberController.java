package com.sctk.cmc.web.controller;

import com.sctk.cmc.auth.domain.SecurityMemberDetails;
import com.sctk.cmc.common.exception.ResponseStatus;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.dto.member.BodyInfoParams;
import com.sctk.cmc.service.dto.member.BodyInfoModifyParams;
import com.sctk.cmc.service.dto.member.MemberDetail;
import com.sctk.cmc.service.abstractions.MemberService;
import com.sctk.cmc.service.dto.member.MemberInfo;
import com.sctk.cmc.web.dto.member.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member", description = "구매자 API Document")
public class MemberController {

    private final MemberService memberService;

    // 구매자 간단 정보 조회
    @GetMapping("/{memberId}/info")
    @Operation(summary = "구매자 간단 정보 조회", description = "디자이너가 요청으로 연결된 구매자의 간단 정보를 조회합니다.")
    public BaseResponse<MemberInfoResponse> getMemberInfo(@PathVariable("memberId") Long memberId) {
        MemberInfo memberInfo = memberService.retrieveInfoById(memberId);

        return new BaseResponse<>(new MemberInfoResponse(
                memberInfo.getName(),
                memberInfo.getProfileImgUrl(),
                memberInfo.getBodyInfoView()
            )
        );
    }

    @GetMapping("/detail")
    @Operation(summary = "구매자 상세 정보 조회", description = "구매자가 자신의 상세 정보를 조회합니다.")
    public BaseResponse<MemberDetailResponse> getMemberDetail() {
        MemberDetail memberDetail = memberService.retrieveDetailById(getMemberId());

        return new BaseResponse<>(new MemberDetailResponse(
                memberDetail.getName(),
                memberDetail.getNickname(),
                memberDetail.getEmail(),
                memberDetail.getProfileImgUrl(),
                memberDetail.getIntroduce()
            )
        );
    }

    @PostMapping("/body-info")
    @Operation(summary = "구매자 신체 정보 등록", description = "구매자의 신체 정보를 등록합니다.")
    public BaseResponse<ResponseStatus> postBodyInfo(@RequestBody BodyInfoPostRequest request) {
        BodyInfoParams bodyInfoParams = new BodyInfoParams(request.getSizes());

        memberService.registerBodyInfo(getMemberId(), bodyInfoParams);

        return new BaseResponse<>(ResponseStatus.SUCCESS);
    }

    @PutMapping("/body-info")
    @Operation(summary = "구매자 신체 정보 수정", description = "구매자 신체 정보를 수정합니다.")
    public BaseResponse<ResponseStatus> putBodyInfo(@RequestBody BodyInfoPutRequest request) {
        BodyInfoModifyParams bodyInfoModifyParams = new BodyInfoModifyParams(request.getSizes());

        memberService.modifyBodyInfo(getMemberId(), bodyInfoModifyParams);

        return new BaseResponse<>(ResponseStatus.SUCCESS);
    }

    @PostMapping("/likes")
    @Operation(summary = "디자니어 좋아요 처리", description = "디자이너에 좋아요 처리를 합니다.")
    public BaseResponse<LikeDesignerResponse> postLikeForDesigner(@RequestParam(name = "designer-id") Long designerId) {
        LikeDesignerResponse response = memberService.like(getMemberId(), designerId);

        return new BaseResponse<>(response);
    }
    private Long getMemberId() {
        SecurityMemberDetails memberDetails = (SecurityMemberDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return memberDetails.getId();
    }
}
