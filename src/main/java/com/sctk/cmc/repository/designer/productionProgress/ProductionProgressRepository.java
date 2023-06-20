package com.sctk.cmc.repository.designer.productionProgress;

import com.sctk.cmc.domain.ProductionProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductionProgressRepository extends JpaRepository<ProductionProgress, Long> {
    @Query("select pp from ProductionProgress pp " +
            "join pp.designer d " +
            "where d.id= :designerId " +
            "and pp.active= true " )
    List<ProductionProgress> findAllByDesignerId(Long designerId);
}
