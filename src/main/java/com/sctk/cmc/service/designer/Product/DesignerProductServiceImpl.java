package com.sctk.cmc.service.designer.Product;

import com.sctk.cmc.common.exception.CMCException;
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
import java.util.stream.Collectors;

import static com.sctk.cmc.common.exception.ResponseStatus.PRODUCT_ILLEGAL_ID;

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
        List<Product> productList = retrieveAllByDesignerId(designerId);

        return productList.stream()
                .map(ProductGetInfoResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public DesignerProductGetDetailResponse retrieveDetailById(Long designerId, Long productId) {
        Product product = retrieveByDesignerIdAndId(designerId, productId);

        List<String> descriptionImgList = convertToUrlList(product);

        return DesignerProductGetDetailResponse.of(product, product.getDesigner(), descriptionImgList);
    }

    private static List<String> convertToUrlList(Product product) {
        return product.getImgs().stream()
                .map(DescriptionImg::getUrl)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> retrieveAllByDesignerId(Long designerId) {
        return designerProductRepository.findAllByDesignerId(designerId);
    }

    @Override
    public Product retrieveByDesignerIdAndId(Long designerId, Long productId) {
        return designerProductRepository.findByDesignerIdAndId(designerId, productId)
                .orElseThrow(() -> new CMCException(PRODUCT_ILLEGAL_ID));
    }
}
