package com.sctk.cmc.service.member.product;

import com.sctk.cmc.domain.Product;
import com.sctk.cmc.controller.member.product.dto.MemberProductGetDetailResponse;
import com.sctk.cmc.controller.member.product.dto.MemberProductGetInfoResponse;

import java.util.List;

public interface MemberProductService {
//    List<MemberProductGetInfoResponse> retrieveAllInfoByDesignerId(Long designerId);

    Product retrieveById(Long productId);

    MemberProductGetInfoResponse retrieveInfoById(Long memberId, Long productId);

    List<MemberProductGetInfoResponse> retrieveAllInfoById(Long memberId);

    MemberProductGetDetailResponse retrieveDetailById(Long memberId, Long productId);

//    List<Product> retrieveAllByDesignerId(Long designerId);
//
//    Product retrieveByDesignerIdAndId(Long designerId, Long productId);
}
