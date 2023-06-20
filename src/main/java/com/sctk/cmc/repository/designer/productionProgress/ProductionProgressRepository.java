package com.sctk.cmc.repository.designer.productionProgress;

import com.sctk.cmc.domain.ProductionProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductionProgressRepository extends JpaRepository<ProductionProgress, Long> {
    @Query("select pp from ProductionProgress pp " +
            "join pp.designer d " +
            "where d.id= :designerId " +
            "and pp.active= true " )
    List<ProductionProgress> findAllByDesignerId(@Param("designerId") Long designerId);

    @Query("select pp from ProductionProgress pp " +
            "join pp.designer d " +
            "left join fetch pp.imgs " +
            "where d.id= :designerId " +
            "and pp.id= :productionProgressId " +
            "and pp.active= true " )
    Optional<ProductionProgress> findWithImgByDesignerIdAndId(@Param("designerId") Long designerId,
                                                              @Param("productionProgressId") Long productionProgressId);
}
