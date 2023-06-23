package com.sctk.cmc.service.member.like.product;

public interface LikeProductService {

    boolean checkMemberLikedProduct(Long memberId, Long productId);
}
