package com.sctk.cmc.controller.member.product;

import com.sctk.cmc.service.member.product.MemberProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member Product", description = "구매자용 상품 관련 API Document")
public class MemberProductController {
    private final MemberProductService memberProductService;

}
