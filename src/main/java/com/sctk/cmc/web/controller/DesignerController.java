package com.sctk.cmc.web.controller;

import com.sctk.cmc.auth.domain.SecurityDesignerDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.abstractions.DesignerService;
import com.sctk.cmc.common.dto.designer.CategoryView;
import com.sctk.cmc.service.dto.designer.*;
import com.sctk.cmc.web.dto.designer.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/{designerId}/categories")
    @Operation(summary = "디자이너 전체 카테고리 조회", description = "디자이너의 카테고리 전체(카테고리, 소재)를 등록합니다.")
    public BaseResponse<CategoryGetResponse> postCategories(@PathVariable("designerId") Long designerId) {
        List<CategoryView> categoryViews = designerService.retrieveAllCategoryViewById(designerId);

        return new BaseResponse<>(new CategoryGetResponse(categoryViews));
    }

    @PostMapping("/categories")
    @Operation(summary = "디자이너 카테고리 등록", description = "디자이너가 자신의 카테고리를 등록합니다.")
    public BaseResponse<CategoryPostResponse> postCategories(@RequestBody CategoryPostRequest request) {
        int registeredHighCategories = designerService.registerCategories(getDesignerId(), request.getCategoryParams());

        return new BaseResponse<>(new CategoryPostResponse(registeredHighCategories));
    }

//    @GetMapping("/rank")
//    @Operation(
//            summary = "인기 디자이너 조회",
//            description = "인기 디자이너를 조회합니다. \n" +
//                    "## Query String\n" +
//                    "  - 범주 : criteria( 카테고리 : \"high-category\" / " + "소재 : \"low-category\") \n" +
//                    "  - 조회 갯수 : limit( int value )"
//    )
//    public BaseResponse<PopularDesignersGetResponse> getPopularDesigners(
//            @RequestParam("criteria") String criteria,
//            @RequestParam("limit") int limit
//    ) {
//        List<PopularDesignerInfo> popularDesignerInfos = designerService.retrievePopularsByCriteria(criteria, limit);
//
//        return new BaseResponse<>(new PopularDesignersGetResponse(popularDesignerInfos));
//        return null;
//    }

    @GetMapping("")
    @Operation(
            summary = "신규 디자이너 조회",
            description = "신규 디자이너를 조회합니다. \n" +
            "## Query String\n" +
            "  - 신규 처리 기간 : joinedlessthan( int value )\n" +
            "  - 조회 갯수 : limit( int value )"
    )
    public BaseResponse<FreshDesignersGetResponse> getFreshDesigners(
            @RequestParam("joinedlessthan") int joinedLessThan,
            @RequestParam("limit") int limit
    ) {
        LocalDate freshOffset = LocalDate.now().minusMonths(1);
        List<FilteredDesignerInfo> freshDesignerInfos = designerService.retrieveAllFreshFrom(freshOffset, limit);

        return new BaseResponse<>(new FreshDesignersGetResponse(freshDesignerInfos));
    }

    private Long getDesignerId() {
        SecurityDesignerDetails designerDetails = (SecurityDesignerDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return designerDetails.getId();
    }
}
