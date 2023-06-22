package com.sctk.cmc.service.designer.product;

import com.sctk.cmc.domain.Product;
import com.sctk.cmc.service.designer.product.dto.DesignerProductGetDetailResponse;
import com.sctk.cmc.service.designer.product.dto.DesignerProductIdResponse;
import com.sctk.cmc.service.designer.product.dto.DesignerProductRegisterParams;
import com.sctk.cmc.service.designer.product.dto.ProductGetInfoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DesignerProductService {
    DesignerProductIdResponse register(Long designerId,
                                       DesignerProductRegisterParams designerProductRegisterParams,
                                       List<MultipartFile> multipartFileList);

    List<ProductGetInfoResponse> retrieveAllInfoByDesignerId(Long designerId);

    DesignerProductGetDetailResponse retrieveDetailById(Long designerId, Long productId);

    List<Product> retrieveAllByDesignerId(Long designerId);

    Product retrieveByDesignerIdAndId(Long designerId, Long productId);
}
