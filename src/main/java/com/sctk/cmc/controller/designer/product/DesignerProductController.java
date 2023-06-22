package com.sctk.cmc.controller.designer.product;

import com.sctk.cmc.auth.domain.SecurityDesignerDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.designer.Product.DesignerProductService;
import com.sctk.cmc.service.designer.Product.dto.DesignerProductIdResponse;
import com.sctk.cmc.service.designer.Product.dto.DesignerProductRegisterParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/designers")
@RequiredArgsConstructor
@Tag(name = "Designer Product", description = "디자이너용 상품 등록 API Document")
public class DesignerProductController {
    private final DesignerProductService designerProductService;

    @Operation(summary = "제작중 상품 전체 간단 조회 API", description = "디자이너가 모든 제작중 상품을 간단 조회할 때 사용합니다.")
    @PostMapping("/product")
    public BaseResponse<DesignerProductIdResponse> register(@AuthenticationPrincipal SecurityDesignerDetails designerDetails,
                                                            @RequestPart(value = "files") List<MultipartFile> multipartFileList,
                                                            @Valid @RequestPart(value = "designerProductRegisterParams") DesignerProductRegisterParams designerProductRegisterParams) {

        Long designerId = designerDetails.getId();
        DesignerProductIdResponse responses = designerProductService.register(designerId, designerProductRegisterParams, multipartFileList);

        return new BaseResponse<>(responses);
    }
}
