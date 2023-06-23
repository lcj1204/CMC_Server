package com.sctk.cmc.service.member.like.handler.function;

import com.sctk.cmc.domain.LikedEntity;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.domain.Product;
import com.sctk.cmc.domain.likeobject.LikeObject;
import com.sctk.cmc.domain.likeobject.LikeProduct;
import com.sctk.cmc.service.member.MemberService;
import com.sctk.cmc.service.member.product.MemberProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeProductFunction implements LikeFunction {
    private final MemberService memberService;
    private final MemberProductService memberProductService;
    @Override
    public boolean support(Class<? extends LikedEntity> targetClass) {
        return targetClass.equals(Product.class);
    }

    @Transactional
    @Override
    public int apply(Long memberId, Long productId, LikeObject likeProduct) {
        LikeProduct like = (LikeProduct) likeProduct;

        if (like != null) {
            return memberService.cancelLike(like);
        }

        Member member = memberService.retrieveById(memberId);
        Product product = memberProductService.retrieveById(productId);

        // 생성자를 통해 연관관계 매핑
        new LikeProduct(member, product);

        return product.getLikeCount();
    }
}
