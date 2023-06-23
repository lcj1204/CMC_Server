package com.sctk.cmc.controller.common.product;

import com.sctk.cmc.auth.domain.SecurityMemberDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.controller.common.product.dto.ProductGetBySearchingResponse;
import com.sctk.cmc.service.common.product.ProductService;
import com.sctk.cmc.service.common.product.dto.ProductGetDetailResponse;
import com.sctk.cmc.service.designer.product.dto.ProductGetInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "상품 관련 API Document")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "디자이너 상품 전체 간단 조회 API", description = "어떤 디자이너의 모든 상품을 간단 조회할 때 사용합니다.")
    @GetMapping("")
    public BaseResponse<List<ProductGetInfoResponse>> retrieveAllInfo(@RequestParam("designerId") Long designerId) {

        List<ProductGetInfoResponse> responses = productService.retrieveAllInfoByDesignerId(designerId);

        return new BaseResponse<>(responses);
    }

    @Operation(summary = "상품 상세 조회 API", description = "상품을 상세 조회할 때 사용합니다.")
    @GetMapping("/{productId}/details")
    public BaseResponse<ProductGetDetailResponse> retrieveDetail(@PathVariable("productId") Long productId) {

        ProductGetDetailResponse responses = productService.retrieveDetailById(productId);

        return new BaseResponse<>(responses);
    }

    @Operation(summary = "검색어(이름 or 태그)를 통한 상품 조회 API", description = "이름 or 태그에 검색어를 포함하는 상품을 조회합니다. Like Count 내림차순")
    @GetMapping("/search")
    public BaseResponse<List<ProductGetBySearchingResponse>> getSearchedProducts(@RequestParam String keyword) {
        List<ProductGetBySearchingResponse> responses = productService.searchAllByKeywordInNameAndTag(keyword);

        return new BaseResponse<>(responses);
    }
}
