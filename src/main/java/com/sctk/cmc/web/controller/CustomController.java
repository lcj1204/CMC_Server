package com.sctk.cmc.web.controller;

import com.sctk.cmc.auth.domain.SecurityDesignerDetails;
import com.sctk.cmc.auth.domain.SecurityMemberDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.abstractions.CustomService;
import com.sctk.cmc.service.dto.custom.CustomParams;
import com.sctk.cmc.service.dto.customResult.CustomResultAcceptParams;
import com.sctk.cmc.service.dto.customResult.CustomResultRejectParams;
import com.sctk.cmc.web.dto.custom.CustomGetDetailResponse;
import com.sctk.cmc.web.dto.custom.CustomGetInfoResponse;
import com.sctk.cmc.web.dto.custom.CustomIdResponse;
import com.sctk.cmc.web.dto.custom.CustomResultIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Custom", description = "커스텀 API Document")
public class CustomController {

    private final CustomService customService;

    @PostMapping("/custom")
    @Operation(summary = "커스텀 요청 생성 API", description = "디자이너에게 커스텀 제작 요청을 생성합니다.")
    public BaseResponse<CustomIdResponse> register(@AuthenticationPrincipal SecurityMemberDetails memberDetails,
                                                   @Valid @RequestBody CustomParams customParams) {

        Long memberId = memberDetails.getId();
        CustomIdResponse response = customService.register(memberId, customParams);

        return new BaseResponse<>(response);
    }

    @GetMapping("/custom")
    @Operation(summary = "커스텀 요청 전체 간단 조회 API", description = "디자이너가 모든 커스텀 요청을 간단 조회할 때 사용합니다.")
    public BaseResponse<List<CustomGetInfoResponse>> retrieveAllInfo(@AuthenticationPrincipal SecurityDesignerDetails designerDetails) {

        Long designerId = designerDetails.getId();
        List<CustomGetInfoResponse> responseList = customService.retrieveAllInfo(designerId);

        return new BaseResponse<>(responseList);
    }

    @GetMapping("/custom/{customId}/detail")
    @Operation(summary = "커스텀 요청 상세 조회 API", description = "디자이너가 커스텀 요청을 상세 조회할 때 사용합니다.")
    public BaseResponse<CustomGetDetailResponse> retrieveDetail(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                                @PathVariable("customId") Long customId) {

        Long designerId = designerDetails.getId();
        CustomGetDetailResponse response = customService.retrieveDetailById(designerId, customId);

        return new BaseResponse<>(response);
    }

    //필요 없나?
    @GetMapping("/custom/{customId}/info")
    @Operation(summary = "커스텀 요청 간단 조회 API", description = "디자이너가 커스텀 요청을 간단 조회할 때 사용합니다.")
    public BaseResponse<CustomGetInfoResponse> retrieveInfo(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                            @PathVariable("customId") Long customId) {

        Long designerId = designerDetails.getId();
        CustomGetInfoResponse response = customService.retrieveInfoById(designerId, customId);

        return new BaseResponse<>(response);
    }

    @DeleteMapping("/custom/{customId}")
    @Operation(summary = "커스텀 요청 삭제(soft) API", description = "디자이너가 커스텀 요청을 수락 또는 거절하면 삭제합니다.")
    public BaseResponse<CustomIdResponse> deleteSoft(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                     @PathVariable("customId") Long customId) {

        Long designerId = designerDetails.getId();
        CustomIdResponse response = customService.deleteSoft(designerId, customId);

        return new BaseResponse<>(response);
    }

    @PostMapping("/custom/{customId}/acceptance")
    @Operation(summary = "커스텀 요청 수락 API", description = "디자이너가 커스텀 요청을 수락합니다.")
    public BaseResponse<CustomResultIdResponse> acceptCustom(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                             @PathVariable("customId") Long customId,
                                                             @Valid @RequestBody CustomResultAcceptParams customResultAcceptParams) {

        Long designerId = designerDetails.getId();
        CustomResultIdResponse response = customService.acceptCustom(designerId, customId, customResultAcceptParams);

        return new BaseResponse<>(response);
    }

    @PostMapping("/custom/{customId}/rejection")
    @Operation(summary = "커스텀 요청 거절 API", description = "디자이너가 커스텀 요청을 거절 합니다.")
    public BaseResponse<CustomResultIdResponse> rejectCustom(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                             @PathVariable("customId") Long customId,
                                                             @Valid @RequestBody CustomResultRejectParams customResultRejectParams) {

        Long designerId = designerDetails.getId();
        CustomResultIdResponse response = customService.rejectCustom(designerId, customId, customResultRejectParams);

        return new BaseResponse<>(response);
    }
}
