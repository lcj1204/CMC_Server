package com.sctk.cmc.repository.common.product;

import com.sctk.cmc.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select distinct p from Product p " +
            "join fetch p.thumbnailImgList " +
            "join p.designer d " +
            "where d.id= :designerId ")
    List<Product> findAllByDesignerId(@Param("designerId") Long designerId);

    @Query("select distinct p from Product p " +
            "join fetch p.designer " +
            "where p.id= :productId " )
    Optional<Product> findWithImgListById(@Param("productId") Long productId);

    @Query("select p from Product p " +
            "join fetch p.designer " +
            "order by p.likeCount desc ")
    List<Product> findAllOrderByLikeCount(Pageable pageable);

    List<Product> findAllByCreatedAtBetweenOrderByCreatedAtAsc(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
