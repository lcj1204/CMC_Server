package com.sctk.cmc.controller.member.product;

import com.sctk.cmc.auth.domain.SecurityMemberDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.controller.member.product.dto.MemberProductLikeGetResponse;
import com.sctk.cmc.service.member.product.MemberProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member Product", description = "구매자용 상품 관련 API Document")
public class MemberProductController {
    private final MemberProductService memberProductService;

    @Operation(summary = "구매자용 상품 좋아요 확인 API", description = "구매자가 해당 상품에 좋아요를 누른기록이 있는지 확인하는 API 입니다..")
    @GetMapping("/product/{productId}/like")
    public BaseResponse<MemberProductLikeGetResponse> checkLiked(@AuthenticationPrincipal SecurityMemberDetails memberDetails,
                                                                 @PathVariable("productId") Long productId) {
        Long memberId = memberDetails.getId();
        MemberProductLikeGetResponse responses = memberProductService.checkLiked(memberId, productId);

        return new BaseResponse<>(responses);
    }
}
