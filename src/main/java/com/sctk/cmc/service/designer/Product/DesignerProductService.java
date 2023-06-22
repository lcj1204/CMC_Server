package com.sctk.cmc.service.designer.Product;

import com.sctk.cmc.service.designer.Product.dto.DesignerProductGetDetailResponse;
import com.sctk.cmc.service.designer.Product.dto.DesignerProductIdResponse;
import com.sctk.cmc.service.designer.Product.dto.DesignerProductRegisterParams;
import com.sctk.cmc.service.designer.Product.dto.ProductGetInfoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DesignerProductService {
    // 상품 등록
    DesignerProductIdResponse register(Long designerId,
                                       DesignerProductRegisterParams designerProductRegisterParams,
                                       List<MultipartFile> multipartFileList);

    // 상품 전체 조회
    List<ProductGetInfoResponse> retrieveAllInfoByDesignerId(Long designerId);

    // 상품 상세 조회
    DesignerProductGetDetailResponse retrieveDetailById(Long designerId, Long productId);

    // 상품 수정

    // 상품 삭제

}
