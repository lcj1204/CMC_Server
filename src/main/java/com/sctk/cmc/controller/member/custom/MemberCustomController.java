package com.sctk.cmc.controller.member.custom;

import com.sctk.cmc.auth.domain.SecurityMemberDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.controller.designer.custom.dto.CustomIdResponse;
import com.sctk.cmc.controller.member.custom.dto.MemberCustomGetInfoResponse;
import com.sctk.cmc.service.member.custom.MemberCustomService;
import com.sctk.cmc.service.member.custom.dto.CustomRegisterParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member Custom", description = "구매자 커스텀 API Document")
public class MemberCustomController {

    private final MemberCustomService memberCustomService;

    @PostMapping("/custom")
    @Operation(summary = "커스텀 요청 생성 API", description = "디자이너에게 커스텀 제작 요청을 생성합니다.")
    public BaseResponse<CustomIdResponse> register(@AuthenticationPrincipal SecurityMemberDetails memberDetails,
                                                   @RequestPart(value = "files") List<MultipartFile> multipartFiles,
                                                   @Valid @RequestPart(value = "customRegisterParams") CustomRegisterParams customRegisterParams) {

        Long memberId = memberDetails.getId();
        CustomIdResponse response = memberCustomService.register(memberId, customRegisterParams, multipartFiles);

        return new BaseResponse<>(response);
    }

    @GetMapping("/custom")
    @Operation(summary = "커스텀 요청 전체 간단조회 API", description = "자신이 보낸 모든 요청들을 간단 조회합니다.")
    public BaseResponse<List<MemberCustomGetInfoResponse>> retrieveAllInfo(@AuthenticationPrincipal SecurityMemberDetails memberDetails) {

        Long memberId = memberDetails.getId();
        List<MemberCustomGetInfoResponse> response = memberCustomService.retrieveAllInfo(memberId);

        return new BaseResponse<>(response);
    }
}
