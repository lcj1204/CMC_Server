package com.sctk.cmc.service.member.like.product;

import com.sctk.cmc.repository.member.likeProduct.LikeProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LikeProductServiceImpl implements LikeProductService{
    private final LikeProductRepository likeProductRepository;

    @Override
    public boolean checkLikeProduct(Long memberId, Long productId) {
        return likeProductRepository.existsByMemberIdAndId(memberId, productId);
    }
}
