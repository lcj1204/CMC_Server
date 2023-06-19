package com.sctk.cmc.repository.designer.productionProgress;

import com.sctk.cmc.domain.ProductionProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionProgressRepository extends JpaRepository<ProductionProgress, Long> {
}
