package com.sctk.cmc.repository.designer.product;

import com.sctk.cmc.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignerProductRepository extends JpaRepository<Product, Long> {
}
