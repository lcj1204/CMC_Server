package com.sctk.cmc.controller.designer.product;

import com.sctk.cmc.auth.domain.SecurityDesignerDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.designer.product.DesignerProductService;
import com.sctk.cmc.service.common.product.dto.ProductGetDetailResponse;
import com.sctk.cmc.service.designer.product.dto.DesignerProductIdResponse;
import com.sctk.cmc.service.designer.product.dto.DesignerProductRegisterParams;
import com.sctk.cmc.service.designer.product.dto.ProductGetInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/designers")
@RequiredArgsConstructor
@Tag(name = "Designer Product", description = "디자이너용 상품 관련 API Document")
public class DesignerProductController {
    private final DesignerProductService designerProductService;

    @Operation(summary = "디자이너용 상품 등록 API", description = "디자이너가 상품을 등록할 때 사용합니다.")
    @PostMapping("/product")
    public BaseResponse<DesignerProductIdResponse> register(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                            @RequestPart(value = "ThumbnailImgs") List<MultipartFile> ThumbnailImgs,
                                                            @RequestPart(value = "DescriptionImgs") List<MultipartFile> DescriptionImgs,
                                                            @Valid @RequestPart(value = "designerProductRegisterParams") DesignerProductRegisterParams designerProductRegisterParams) {

        Long designerId = designerDetails.getId();
        DesignerProductIdResponse responses = designerProductService.register(designerId, designerProductRegisterParams, ThumbnailImgs, DescriptionImgs);

        return new BaseResponse<>(responses);
    }

    @Operation(summary = "디자이너용 상품 전체 간단 조회 API", description = "디자이너가 본인이 등록한 모든 상품을 간단 조회할 때 사용합니다.")
    @GetMapping("/product")
    public BaseResponse<List<ProductGetInfoResponse>> retrieveAllInfo(@AuthenticationPrincipal SecurityDesignerDetails designerDetails) {

        Long designerId = designerDetails.getId();
        List<ProductGetInfoResponse> responses = designerProductService.retrieveAllInfoByDesignerId(designerId);

        return new BaseResponse<>(responses);
    }

    @Operation(summary = "디자이너용 상품 상세 조회 API", description = "디자이너가 본인이 등록한 상품을 상세 조회할 때 사용합니다.")
    @GetMapping("/product/{productId}/details")
    public BaseResponse<ProductGetDetailResponse> retrieveAllInfo(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                                  @PathVariable("productId") Long productId) {

        Long designerId = designerDetails.getId();
        ProductGetDetailResponse responses = designerProductService.retrieveDetailById(designerId, productId);

        return new BaseResponse<>(responses);
    }
}
