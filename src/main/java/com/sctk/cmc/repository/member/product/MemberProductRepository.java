package com.sctk.cmc.repository.member.product;

import com.sctk.cmc.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberProductRepository extends JpaRepository<Product, Long> {
    @Query("select distinct p from Product p " +
            "join fetch p.imgs " +
            "join fetch p.designer d " +
            "where p.id= :productId ")
    Optional<Product> findByDesignerIdAndId(@Param("productId") Long productId);
}
