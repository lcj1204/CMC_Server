package com.sctk.cmc.service.common.product;

import com.sctk.cmc.domain.Product;
import com.sctk.cmc.service.common.product.dto.ProductGetDetailResponse;
import com.sctk.cmc.service.designer.product.dto.ProductGetInfoResponse;

import java.util.List;

public interface ProductService {

    List<ProductGetInfoResponse> retrieveAllInfoByDesignerId(Long designerId);

    ProductGetDetailResponse retrieveDetailById(Long productId);

    List<Product> retrieveAllByDesignerId(Long designerId);

    Product retrieveByDesignerIdAndId(Long productId);
}
