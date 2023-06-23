package com.sctk.cmc.service.designer.product;

import com.sctk.cmc.domain.DescriptionImg;
import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Product;
import com.sctk.cmc.domain.ThumbnailImg;
import com.sctk.cmc.repository.common.product.ProductRepository;
import com.sctk.cmc.service.designer.DesignerService;
import com.sctk.cmc.service.designer.product.dto.DesignerProductIdResponse;
import com.sctk.cmc.service.designer.product.dto.DesignerProductRegisterParams;
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
    private final ProductRepository productRepository;
    private final AmazonS3Service amazonS3Service;

    @Override
    @Transactional
    public DesignerProductIdResponse register(Long designerId,
                                              DesignerProductRegisterParams designerProductRegisterParams,
                                              List<MultipartFile> ThumbnailImgs,
                                              List<MultipartFile> DescriptionImgs) {

        Designer designer = designerService.retrieveById(designerId);

        Product createdProduct = Product.create(designer,
                                                designerProductRegisterParams.getName(),
                                                designerProductRegisterParams.getPrice(),
                                                designerProductRegisterParams.getTag(),
                                                designerProductRegisterParams.getDescription());

        Product savedProduct = productRepository.save(createdProduct);

        List<String> ThumbnailImgUrls = amazonS3Service.uploadProductThumbnailImgs(ThumbnailImgs, designerId, savedProduct.getId());
        List<String> DescriptionImgUrls = amazonS3Service.uploadProductDescriptionImgs(DescriptionImgs, designerId, savedProduct.getId());

        ThumbnailImgUrls.forEach(u -> ThumbnailImg.create(u, savedProduct));
        DescriptionImgUrls.forEach(u -> DescriptionImg.create(u, savedProduct));

        return DesignerProductIdResponse.of(savedProduct);
    }
}
