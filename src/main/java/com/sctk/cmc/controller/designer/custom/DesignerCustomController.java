package com.sctk.cmc.controller.designer.custom;

import com.sctk.cmc.auth.domain.SecurityDesignerDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.controller.designer.custom.dto.CustomGetInfoResponse;
import com.sctk.cmc.controller.designer.custom.dto.CustomResultIdResponse;
import com.sctk.cmc.service.designer.custom.DesignerCustomService;
import com.sctk.cmc.service.designer.custom.dto.CustomResultAcceptParams;
import com.sctk.cmc.service.designer.custom.dto.CustomResultRejectParams;
import com.sctk.cmc.controller.designer.custom.dto.CustomGetDetailResponse;
import com.sctk.cmc.controller.designer.custom.dto.CustomIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/designers")
@RequiredArgsConstructor
@Tag(name = "Designer Custom", description = "디자이너 커스텀 API Document")
public class DesignerCustomController {

    private final DesignerCustomService designerCustomService;

    @GetMapping("/custom")
    @Operation(summary = "커스텀 요청 전체 간단 조회 API", description = "디자이너가 모든 커스텀 요청을 간단 조회할 때 사용합니다.")
    public BaseResponse<List<CustomGetInfoResponse>> retrieveAllInfo(@AuthenticationPrincipal SecurityDesignerDetails designerDetails) {

        Long designerId = designerDetails.getId();
        List<CustomGetInfoResponse> responseList = designerCustomService.retrieveAllInfo(designerId);

        return new BaseResponse<>(responseList);
    }

    @GetMapping("/custom/{customId}/detail")
    @Operation(summary = "커스텀 요청 상세 조회 API", description = "디자이너가 커스텀 요청을 상세 조회할 때 사용합니다.")
    public BaseResponse<CustomGetDetailResponse> retrieveDetail(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                                @PathVariable("customId") Long customId) {

        Long designerId = designerDetails.getId();
        CustomGetDetailResponse response = designerCustomService.retrieveDetailById(designerId, customId);

        return new BaseResponse<>(response);
    }

    @DeleteMapping("/custom/{customId}")
    @Operation(summary = "커스텀 요청 삭제(soft) API", description = "디자이너가 커스텀 요청을 수락 또는 거절하면 삭제합니다.")
    public BaseResponse<CustomIdResponse> deleteSoft(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                     @PathVariable("customId") Long customId) {

        Long designerId = designerDetails.getId();
        CustomIdResponse response = designerCustomService.deleteSoft(designerId, customId);

        return new BaseResponse<>(response);
    }

    @PostMapping("/custom/{customId}/acceptance")
    @Operation(summary = "커스텀 요청 수락 API", description = "디자이너가 커스텀 요청을 수락합니다.")
    public BaseResponse<CustomResultIdResponse> acceptCustom(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                             @PathVariable("customId") Long customId,
                                                             @Valid @RequestBody CustomResultAcceptParams customResultAcceptParams) {

        Long designerId = designerDetails.getId();
        CustomResultIdResponse response = designerCustomService.acceptCustom(designerId, customId, customResultAcceptParams);

        return new BaseResponse<>(response);
    }

    @PostMapping("/custom/{customId}/rejection")
    @Operation(summary = "커스텀 요청 거절 API", description = "디자이너가 커스텀 요청을 거절 합니다.")
    public BaseResponse<CustomResultIdResponse> rejectCustom(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                             @PathVariable("customId") Long customId,
                                                             @Valid @RequestBody CustomResultRejectParams customResultRejectParams) {

        Long designerId = designerDetails.getId();
        CustomResultIdResponse response = designerCustomService.rejectCustom(designerId, customId, customResultRejectParams);

        return new BaseResponse<>(response);
    }
}
