package com.sctk.cmc.repository.designer.product;

import com.sctk.cmc.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DesignerProductRepository extends JpaRepository<Product, Long> {
    @Query("select distinct p from Product p " +
            "join fetch p.imgs " +
            "join p.designer d " +
            "where d.id= :designerId ")
    List<Product> findAllByDesignerId(@Param("designerId") Long designerId);

    @Query("select distinct p from Product p " +
            "join fetch p.imgs " +
            "join fetch p.designer d " +
            "where d.id= :designerId " +
            "and p.id= :productId ")
    Optional<Product> findByDesignerIdAndId(@Param("designerId") Long designerId, @Param("productId") Long productId);
}
