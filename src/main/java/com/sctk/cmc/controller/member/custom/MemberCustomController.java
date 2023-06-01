package com.sctk.cmc.controller.member.custom;

import com.sctk.cmc.auth.domain.SecurityMemberDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.controller.designer.custom.dto.CustomIdResponse;
import com.sctk.cmc.service.member.custom.MemberCustomService;
import com.sctk.cmc.service.member.custom.dto.CustomRegisterParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member Custom", description = "구매자 커스텀 API Document")
public class MemberCustomController {

    private final MemberCustomService memberCustomService;

    @PostMapping("/custom")
    @Operation(summary = "커스텀 요청 생성 API", description = "디자이너에게 커스텀 제작 요청을 생성합니다.")
    public BaseResponse<CustomIdResponse> register(@AuthenticationPrincipal SecurityMemberDetails memberDetails,
                                                   @Valid @RequestBody CustomRegisterParams customRegisterParams) {

        Long memberId = memberDetails.getId();
        CustomIdResponse response = memberCustomService.register(memberId, customRegisterParams);

        return new BaseResponse<>(response);
    }
}
