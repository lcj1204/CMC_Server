package com.sctk.cmc.service.member.product;

import com.sctk.cmc.service.member.product.dto.MemberProductGetDetailResponse;

public interface MemberProductService {
//    List<MemberProductGetInfoResponse> retrieveAllInfoByDesignerId(Long designerId);

    MemberProductGetDetailResponse retrieveDetailById(Long memberId, Long productId);

//    List<Product> retrieveAllByDesignerId(Long designerId);
//
//    Product retrieveByDesignerIdAndId(Long designerId, Long productId);
}
