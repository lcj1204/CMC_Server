package com.sctk.cmc.service.member.product;

import com.sctk.cmc.controller.member.product.dto.LikeProductGetExistenceResponse;
import com.sctk.cmc.controller.member.product.dto.MemberProductGetInfoResponse;
import com.sctk.cmc.domain.Product;

import java.util.List;

public interface MemberProductService {
    Product retrieveById(Long productId);

    MemberProductGetInfoResponse retrieveInfoById(Long memberId, Long productId);

    List<MemberProductGetInfoResponse> retrieveAllInfoById(Long memberId);

    LikeProductGetExistenceResponse checkLiked(Long memberId, Long productId);
}
