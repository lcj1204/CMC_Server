package com.sctk.cmc.web.controller;

import com.sctk.cmc.auth.domain.SecurityDesignerDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.abstractions.DesignerService;
import com.sctk.cmc.service.dto.designer.DesignerInfo;
import com.sctk.cmc.web.dto.designer.CategoryPostRequest;
import com.sctk.cmc.web.dto.designer.CategoryPostResponse;
import com.sctk.cmc.web.dto.designer.DesignerInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/designers")
@RequiredArgsConstructor
@Tag(name = "Designer", description = "디자이너 API Document")
public class DesignerController {
    private final DesignerService designerService;

    @GetMapping("/{designerId}/info")
    @Operation(summary = "디자이너 간단 정보 조회", description = "디자이너의 간단 정보를 조회합니다.")
    public BaseResponse<DesignerInfoResponse> getDesignerInfo(@PathVariable("designerId") Long designerId) {
        DesignerInfo designerInfo = designerService.retrieveInfoById(designerId);

        return new BaseResponse<>(
                new DesignerInfoResponse(
                        designerInfo.getName(),
                        designerInfo.getProfileImgUrl(),
                        designerInfo.getIntroduce(),
                        designerInfo.getLikes(),
                        designerInfo.getHighCategoryNames(),
                        designerInfo.getLowCategoryNames()
                )
        );
    }

    @PostMapping("/categories")
    @Operation(summary = "디자이너 카테고리 등록", description = "디자이너가 자신의 카테고리를 등록합니다.")
    public BaseResponse<CategoryPostResponse> postCategories(@RequestBody CategoryPostRequest request) {
        int registeredHighCategories = designerService.registerCategories(getDesignerId(), request.getCategoryParams());

        return new BaseResponse<>(new CategoryPostResponse(registeredHighCategories));
    }

    private Long getDesignerId() {
        SecurityDesignerDetails designerDetails = (SecurityDesignerDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return designerDetails.getId();
    }
}
