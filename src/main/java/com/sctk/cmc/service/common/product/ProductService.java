package com.sctk.cmc.service.common.product;

import com.sctk.cmc.controller.common.product.dto.ProductGetBySearchingResponse;
import com.sctk.cmc.domain.Product;
import com.sctk.cmc.service.common.product.dto.ProductGetDetailResponse;
import com.sctk.cmc.service.designer.product.dto.ProductGetInfoResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {

    List<ProductGetInfoResponse> retrieveAllInfoByDesignerId(Long designerId);

    ProductGetDetailResponse retrieveDetailById(Long productId);

    List<Product> retrieveAllByDesignerId(Long designerId);

    Product retrieveByDesignerIdAndId(Long productId);

    List<ProductGetBySearchingResponse> searchAllByKeywordInNameAndTag(String keyword);

    List<ProductGetBySearchingResponse> retrieveAllOrderByLikeCount(Pageable pageable);

    List<ProductGetBySearchingResponse> retrieveAllByStartAndEndDate(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
