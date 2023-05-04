package com.sctk.cmc.web.controller;

import com.sctk.cmc.common.exception.ResponseStatus;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.dto.member.BodyInfoParams;
import com.sctk.cmc.service.dto.member.BodyInfoModifyParams;
import com.sctk.cmc.service.dto.member.MemberDetails;
import com.sctk.cmc.service.abstractions.MemberService;
import com.sctk.cmc.service.dto.member.MemberInfo;
import com.sctk.cmc.web.dto.BodyInfoPostRequest;
import com.sctk.cmc.web.dto.BodyInfoPutRequest;
import com.sctk.cmc.web.dto.MemberDetailsResponse;
import com.sctk.cmc.web.dto.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    // 구매자 간단 정보 조회
    @GetMapping("/{memberId}/info")
    public BaseResponse<MemberInfoResponse> getMemberInfo(@PathVariable("memberId") Long memberId) {
        MemberInfo memberInfo = memberService.retrieveInfoById(memberId);

        return new BaseResponse<>(new MemberInfoResponse(
                memberInfo.getName(),
                memberInfo.getProfileImgUrl(),
                memberInfo.getBodyInfoView()
            )
        );
    }

    // 구매자 상세 정보 조회
    @GetMapping("/detail")
    public BaseResponse<MemberDetailsResponse> getMemberDetails() {
        MemberDetails memberDetails = memberService.retrieveDetailsById(getMemberId());

        return new BaseResponse<>(new MemberDetailsResponse(
                memberDetails.getName(),
                memberDetails.getNickname(),
                memberDetails.getEmail(),
                memberDetails.getProfileImgUrl(),
                memberDetails.getIntroduce()
            )
        );
    }

    // 구매자 신체 정보 등록
    @PostMapping("/body-info")
    public BaseResponse<ResponseStatus> postBodyInfo(@RequestBody BodyInfoPostRequest request) {
        BodyInfoParams bodyInfoParams = BodyInfoParams.builder()
                .height(request.getHeight())
                .hip(request.getHip())
                .lower(request.getLower())
                .upper(request.getUpper())
                .waist(request.getWaist())
                .chest(request.getChest())
                .thigh(request.getThigh())
                .weight(request.getWeight())
                .shoulder(request.getShoulder())
                .build();

        memberService.registerBodyInfo(getMemberId(), bodyInfoParams);

        return new BaseResponse<>(ResponseStatus.SUCCESS);
    }

    // 구매자 신체 정보 수정
    @PutMapping("/body-info")
    public BaseResponse<ResponseStatus> putBodyInfo(@RequestBody BodyInfoPutRequest request) {
        BodyInfoModifyParams bodyInfoModifyParams = BodyInfoModifyParams.builder()
                .height(request.getHeight())
                .hip(request.getHip())
                .lower(request.getLower())
                .upper(request.getUpper())
                .waist(request.getWaist())
                .chest(request.getChest())
                .thigh(request.getThigh())
                .weight(request.getWeight())
                .shoulder(request.getShoulder())
                .build();

        memberService.modifyBodyInfo(getMemberId(), bodyInfoModifyParams);

        return new BaseResponse<>(ResponseStatus.SUCCESS);
    }

    private Long getMemberId() {
        return Long.parseLong(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());
    }
}
