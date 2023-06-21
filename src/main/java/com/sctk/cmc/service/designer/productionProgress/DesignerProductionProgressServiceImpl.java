package com.sctk.cmc.service.designer.productionProgress;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.domain.ProductionProgress;
import com.sctk.cmc.domain.ProductionProgressImg;
import com.sctk.cmc.domain.ProgressType;
import com.sctk.cmc.repository.designer.productionProgress.DesignerProductionProgressRepository;
import com.sctk.cmc.service.designer.productionProgress.dto.DesignerProductionProgressGetDetailResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.DesignerProductionProgressGetInfoResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.DesignerProductionProgressIdResponse;
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
public class DesignerProductionProgressServiceImpl implements DesignerProductionProgressService {
    private final DesignerProductionProgressRepository designerProductionProgressRepository;
    private final AmazonS3Service amazonS3Service;

    @Override
    public List<DesignerProductionProgressGetInfoResponse> retrieveAllInfo(Long designerId) {
        List<ProductionProgress> allProductionProgress = retrieveAllProductionProgress(designerId);

        List<DesignerProductionProgressGetInfoResponse> responseList = allProductionProgress.stream()
                .map(DesignerProductionProgressGetInfoResponse::of)
                .collect(Collectors.toList());

        return responseList;
    }

    @Override
    public DesignerProductionProgressGetDetailResponse retrieveDetailById(Long designerId, Long productionProgressId) {

        ProductionProgress productionProgress = retrieveProductionProgress(designerId, productionProgressId);

        DesignerProductionProgressGetInfoResponse productionProgressInfo
                = DesignerProductionProgressGetInfoResponse.of(productionProgress);

        Map<ProgressType, List<String>> progressTypeListMap = retrieveProductionProgressAllImg(productionProgress);

        return DesignerProductionProgressGetDetailResponse.of(productionProgressInfo, progressTypeListMap);
    }

    @Override
    @Transactional
    public DesignerProductionProgressIdResponse registerProductionProgressImage(Long designerId,
                                                                                Long productionProgressId, String progressType,
                                                                                List<MultipartFile> multipartFileList) {

        ProductionProgress productionProgress = retrieveProductionProgress(designerId, productionProgressId);

        ProgressType progressTypeEnum = strProgressTypeToEnum(progressType);

        List<String> uploadUrls = amazonS3Service.uploadProductionProgressImgs(multipartFileList, designerId,
                                                                            productionProgressId, progressType);

        uploadUrls.forEach(url -> ProductionProgressImg.create(url, progressTypeEnum, productionProgress));

        return DesignerProductionProgressIdResponse.of(productionProgress.getId());
    }

    private List<ProductionProgress> retrieveAllProductionProgress(Long designerId) {
        return designerProductionProgressRepository.findAllByDesignerId(designerId);
    }

    private ProductionProgress retrieveProductionProgress(Long designerId, Long productionProgressId) {
        return designerProductionProgressRepository.findWithImgByDesignerIdAndId(designerId, productionProgressId)
        .orElseThrow(() -> new CMCException(PRODUCTION_PROGRESS_ILLEGAL_ID));
    }

    private static Map<ProgressType, List<String>> retrieveProductionProgressAllImg(ProductionProgress productionProgress) {
        return productionProgress.getImgs().stream()
                .filter(i -> !i.getType().equals(ProgressType.ACCEPT))
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
