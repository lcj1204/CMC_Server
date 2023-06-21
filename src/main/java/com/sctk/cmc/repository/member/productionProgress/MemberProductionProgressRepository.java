package com.sctk.cmc.repository.member.productionProgress;

import com.sctk.cmc.domain.ProductionProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberProductionProgressRepository extends JpaRepository<ProductionProgress, Long> {
    @Query("select pp from ProductionProgress pp " +
            "join pp.member m " +
            "join fetch pp.designer d " +
            "where m.id= :memberId " +
            "and pp.active= true " )
    List<ProductionProgress> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("select pp from ProductionProgress pp " +
            "join pp.member m " +
            "join fetch pp.designer d " +
            "left join fetch pp.imgs " +
            "where m.id= :memberId " +
            "and pp.id= :productionProgressId " +
            "and pp.active= true " )
    Optional<ProductionProgress> findWithImgByMemberIdAndId(@Param("memberId") Long memberId,
                                                            @Param("productionProgressId") Long productionProgressId);
}
