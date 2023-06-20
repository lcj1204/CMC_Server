package com.sctk.cmc.controller.designer.productionProgress;

import com.sctk.cmc.auth.domain.SecurityDesignerDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.controller.designer.productionProgress.dto.ProductionProgressTypeParams;
import com.sctk.cmc.service.designer.productionProgress.ProductionProgressService;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressGetDetailResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressGetInfoResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressIdResponse;
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
public class ProductionProgressController {
    private final ProductionProgressService productionProgressService;

    @Operation(summary = "제작중 상품 전체 간단 조회 API", description = "디자이너가 모든 제작중 상품을 간단 조회할 때 사용합니다.")
    @GetMapping("/production-progress")
    public BaseResponse<List<ProductionProgressGetInfoResponse>> retrieveAllInfo(@AuthenticationPrincipal SecurityDesignerDetails designerDetails) {

        Long designerId = designerDetails.getId();
        List<ProductionProgressGetInfoResponse> responses = productionProgressService.retrieveAllInfo(designerId);

        return new BaseResponse<>(responses);
    }

    @Operation(summary = "제작중 상품 상세 조회 API", description = "디자이너가 제작중인 상품을 상세 조회할 때 사용합니다.")
    @GetMapping("/production-progress/{productionProgressId}/detail")
    public BaseResponse<ProductionProgressGetDetailResponse> retrieveDetail(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                                            @PathVariable("productionProgressId") Long productionProgressId) {

        Long designerId = designerDetails.getId();
        ProductionProgressGetDetailResponse responses = productionProgressService.retrieveDetailById(designerId, productionProgressId);

        return new BaseResponse<>(responses);
    }

    @Operation(summary = "제작중 상품 이미지 업로드 API", description = "디자이너가 제작중인 상품의 진행 상황별 이미지 업도르할 때 사용합니다.")
    @PostMapping("/production-progress/{productionProgressId}/detail/image")
    public BaseResponse<ProductionProgressIdResponse> registerProductionProgressImage(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                                                      @PathVariable("productionProgressId") Long productionProgressId,
                                                                                      @RequestPart("files") List<MultipartFile> multipartFileList,
                                                                                      @RequestPart("ProductionProgressTypeParams") ProductionProgressTypeParams productionProgressTypeParams) {

        Long designerId = designerDetails.getId();
        String progressType = productionProgressTypeParams.getProgressType();
        ProductionProgressIdResponse responses
                = productionProgressService.registerProductionProgressImage(designerId, productionProgressId,
                                                                            progressType, multipartFileList);

        return new BaseResponse<>(responses);
    }
}
