package com.sctk.cmc.service.member.product;

import com.sctk.cmc.domain.Product;
import com.sctk.cmc.service.member.product.dto.MemberProductGetDetailResponse;

public interface MemberProductService {
//    List<MemberProductGetInfoResponse> retrieveAllInfoByDesignerId(Long designerId);

    Product retrieveById(Long productId);
    MemberProductGetDetailResponse retrieveDetailById(Long memberId, Long productId);

//    List<Product> retrieveAllByDesignerId(Long designerId);
//
//    Product retrieveByDesignerIdAndId(Long designerId, Long productId);
}
