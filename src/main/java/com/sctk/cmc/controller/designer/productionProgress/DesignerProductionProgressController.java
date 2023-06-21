package com.sctk.cmc.controller.designer.productionProgress;

import com.sctk.cmc.auth.domain.SecurityDesignerDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.controller.designer.productionProgress.dto.ProductionProgressTypeParams;
import com.sctk.cmc.service.designer.productionProgress.DesignerProductionProgressService;
import com.sctk.cmc.service.designer.productionProgress.dto.DesignerProductionProgressGetDetailResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.DesignerProductionProgressGetInfoResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.DesignerProductionProgressIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/designers")
@RequiredArgsConstructor
@Tag(name = "Designer Production Progress", description = "디자이너 제작중 상품 API Document")
public class DesignerProductionProgressController {
    private final DesignerProductionProgressService designerProductionProgressService;

    @Operation(summary = "제작중 상품 전체 간단 조회 API", description = "디자이너가 모든 제작중 상품을 간단 조회할 때 사용합니다.")
    @GetMapping("/production-progress")
    public BaseResponse<List<DesignerProductionProgressGetInfoResponse>> retrieveAllInfo(@AuthenticationPrincipal SecurityDesignerDetails designerDetails) {

        Long designerId = designerDetails.getId();
        List<DesignerProductionProgressGetInfoResponse> responses = designerProductionProgressService.retrieveAllInfo(designerId);

        return new BaseResponse<>(responses);
    }

    @Operation(summary = "제작중 상품 상세 조회 API", description = "디자이너가 제작중인 상품을 상세 조회할 때 사용합니다.")
    @GetMapping("/production-progress/{productionProgressId}/detail")
    public BaseResponse<DesignerProductionProgressGetDetailResponse> retrieveDetail(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                                                    @PathVariable("productionProgressId") Long productionProgressId) {

        Long designerId = designerDetails.getId();
        DesignerProductionProgressGetDetailResponse responses = designerProductionProgressService.retrieveDetailById(designerId, productionProgressId);

        return new BaseResponse<>(responses);
    }

    @Operation(summary = "제작중 상품 이미지 업로드 API", description = "디자이너가 제작중인 상품의 진행 상황별 이미지 업도르할 때 사용합니다.")
    @PostMapping("/production-progress/{productionProgressId}/detail/image")
    public BaseResponse<DesignerProductionProgressIdResponse> registerProductionProgressImage(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                                                              @PathVariable("productionProgressId") Long productionProgressId,
                                                                                              @RequestPart("files") List<MultipartFile> multipartFileList,
                                                                                              @RequestPart("ProductionProgressTypeParams") ProductionProgressTypeParams productionProgressTypeParams) {

        Long designerId = designerDetails.getId();
        String progressType = productionProgressTypeParams.getProgressType();
        DesignerProductionProgressIdResponse responses
                = designerProductionProgressService.registerProductionProgressImage(designerId, productionProgressId,
                                                                            progressType, multipartFileList);

        return new BaseResponse<>(responses);
    }
}
