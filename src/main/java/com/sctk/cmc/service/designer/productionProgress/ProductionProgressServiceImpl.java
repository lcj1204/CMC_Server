package com.sctk.cmc.service.designer.productionProgress;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.domain.ProductionProgress;
import com.sctk.cmc.domain.ProductionProgressImg;
import com.sctk.cmc.domain.ProgressType;
import com.sctk.cmc.repository.designer.productionProgress.ProductionProgressRepository;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressGetDetailResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressGetInfoResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressIdResponse;
import com.sctk.cmc.util.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sctk.cmc.common.exception.ResponseStatus.PRODUCTION_PROGRESS_ILLEGAL_ID;
import static com.sctk.cmc.common.exception.ResponseStatus.PRODUCTION_PROGRESS_ILLEGAL_TYPE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductionProgressServiceImpl implements ProductionProgressService {
    private final ProductionProgressRepository productionProgressRepository;
    private final AmazonS3Service amazonS3Service;

    @Override
    public List<ProductionProgressGetInfoResponse> retrieveAllInfo(Long designerId) {
        List<ProductionProgress> allProductionProgress = retrieveAllProductionProgress(designerId);

        List<ProductionProgressGetInfoResponse> responseList = allProductionProgress.stream()
                .map(ProductionProgressGetInfoResponse::of)
                .collect(Collectors.toList());

        return responseList;
    }

    @Override
    public ProductionProgressGetDetailResponse retrieveDetailById(Long designerId, Long productionProgressId) {

        ProductionProgress productionProgress = retrieveProductionProgress(designerId, productionProgressId);

        ProductionProgressGetInfoResponse productionProgressInfo
                = ProductionProgressGetInfoResponse.of(productionProgress);

        Map<ProgressType, List<String>> progressTypeListMap = retrieveProductionProgressAllImg(productionProgress);

        return ProductionProgressGetDetailResponse.of(productionProgressInfo, progressTypeListMap);
    }

    @Override
    @Transactional
    public ProductionProgressIdResponse registerProductionProgressImage(Long designerId,
                                                                        Long productionProgressId, String progressType,
                                                                        List<MultipartFile> multipartFileList) {

        ProductionProgress productionProgress = retrieveProductionProgress(designerId, productionProgressId);

        ProgressType progressTypeEnum = strProgressTypeToEnum(progressType);

        List<String> uploadUrls = amazonS3Service.uploadProductionProgressImgs(multipartFileList, designerId,
                                                                            productionProgressId, progressType);

        uploadUrls.forEach(url -> ProductionProgressImg.create(url, progressTypeEnum, productionProgress));

        return ProductionProgressIdResponse.of(productionProgress.getId());
    }

    private List<ProductionProgress> retrieveAllProductionProgress(Long designerId) {
        return productionProgressRepository.findAllByDesignerId(designerId);
    }

    private ProductionProgress retrieveProductionProgress(Long designerId, Long productionProgressId) {
        return productionProgressRepository.findWithImgByDesignerIdAndId(designerId, productionProgressId)
        .orElseThrow(() -> new CMCException(PRODUCTION_PROGRESS_ILLEGAL_ID));
    }

    private static Map<ProgressType, List<String>> retrieveProductionProgressAllImg(ProductionProgress productionProgress) {
        return productionProgress.getImgs().stream()
                        .collect(Collectors.groupingBy(ProductionProgressImg::getType,
                        Collectors.mapping(ProductionProgressImg::getUrl, Collectors.toList())));
    }

    private ProgressType strProgressTypeToEnum(String progressType) {
        ProgressType progressTypeEnum = ProgressType.convertedFromString(progressType);
        if (progressTypeEnum.equals(ProgressType.ACCEPT)) {
            throw new CMCException(PRODUCTION_PROGRESS_ILLEGAL_TYPE);
        }
        return progressTypeEnum;
    }
}
