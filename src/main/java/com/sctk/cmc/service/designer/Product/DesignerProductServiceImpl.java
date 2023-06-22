package com.sctk.cmc.service.designer.Product;

import com.sctk.cmc.domain.DescriptionImg;
import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Product;
import com.sctk.cmc.repository.designer.product.DesignerProductRepository;
import com.sctk.cmc.service.designer.DesignerService;
import com.sctk.cmc.service.designer.Product.dto.DesignerProductGetDetailResponse;
import com.sctk.cmc.service.designer.Product.dto.DesignerProductIdResponse;
import com.sctk.cmc.service.designer.Product.dto.DesignerProductRegisterParams;
import com.sctk.cmc.service.designer.Product.dto.ProductGetInfoResponse;
import com.sctk.cmc.util.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DesignerProductServiceImpl implements DesignerProductService{
    private final DesignerService designerService;
    private final DesignerProductRepository designerProductRepository;
    private final AmazonS3Service amazonS3Service;

    @Override
    @Transactional
    public DesignerProductIdResponse register(Long designerId,
                                              DesignerProductRegisterParams designerProductRegisterParams,
                                              List<MultipartFile> multipartFileList) {

        Designer designer = designerService.retrieveById(designerId);

        Product createdProduct = Product.create(designer,
                                                designerProductRegisterParams.getName(),
                                                designerProductRegisterParams.getPrice(),
                                                designerProductRegisterParams.getTag(),
                                                designerProductRegisterParams.getDescription());

        Product savedProduct = designerProductRepository.save(createdProduct);

        List<String> uploadUrls
                = amazonS3Service.uploadProductImgs(multipartFileList, designerId, savedProduct.getId());

        uploadUrls.forEach(u -> DescriptionImg.create(u, savedProduct));

        return DesignerProductIdResponse.of(savedProduct);
    }

    @Override
    public List<ProductGetInfoResponse> retrieveAllInfoByDesignerId(Long designerId) {
        return null;
    }

    @Override
    public DesignerProductGetDetailResponse retrieveDetailById(Long designerId, Long productId) {
        return null;
    }
}
