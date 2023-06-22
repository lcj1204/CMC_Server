package com.sctk.cmc.repository.member.likeProduct;

import com.sctk.cmc.domain.LikeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeProductRepository extends JpaRepository<LikeProduct, Long> {
    boolean existsByMemberIdAndId(Long memberId, Long productId);
}
