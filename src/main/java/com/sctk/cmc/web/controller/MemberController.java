package com.sctk.cmc.web.controller;

import com.sctk.cmc.common.exception.ResponseStatus;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.dto.BodyInfoParams;
import com.sctk.cmc.service.dto.member.MemberDetails;
import com.sctk.cmc.service.abstractions.MemberService;
import com.sctk.cmc.web.dto.BodyInfoRegisterRequest;
import com.sctk.cmc.web.dto.MemberDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/detail")
    public BaseResponse<MemberDetailsResponse> getMemberDetails() {
        MemberDetails memberDetails = memberService.retrieveById(getMemberId());

        MemberDetailsResponse detailsResponse = new MemberDetailsResponse(
                memberDetails.getName(),
                memberDetails.getNickname(),
                memberDetails.getEmail(),
                memberDetails.getProfileImgUrl(),
                memberDetails.getIntroduce());

        return new BaseResponse<>(detailsResponse);
    }

    @PostMapping("/body-info")
    public BaseResponse<ResponseStatus> registerBodyInfo(@RequestBody BodyInfoRegisterRequest request) {
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

    private Long getMemberId() {
        return Long.parseLong(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());
    }
}
