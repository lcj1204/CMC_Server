package com.sctk.cmc.service.designer.productionProgress;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.domain.ProductionProgress;
import com.sctk.cmc.domain.ProductionProgressImg;
import com.sctk.cmc.domain.ProgressType;
import com.sctk.cmc.repository.designer.productionProgress.ProductionProgressRepository;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressGetDetailResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressGetInfoResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressIdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sctk.cmc.common.exception.ResponseStatus.PRODUCTION_PROGRESS_ILLEGAL_ID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductionProgressServiceImpl implements ProductionProgressService {
    private final ProductionProgressRepository productionProgressRepository;

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

        Map<ProgressType, String> productionProgressImgMap = retrieveProductionProgressAllImg(productionProgress);

        return ProductionProgressGetDetailResponse.of(productionProgressInfo, productionProgressImgMap);
    }

    @Override
    public ProductionProgressIdResponse registerProductionProgressImage(Long designerId, Long customId, MultipartFile multipartFile, ProgressType type) {
        return null;
    }

    private List<ProductionProgress> retrieveAllProductionProgress(Long designerId) {
        return productionProgressRepository.findAllByDesignerId(designerId);
    }

    private ProductionProgress retrieveProductionProgress(Long designerId, Long productionProgressId) {
        return productionProgressRepository.findWithImgByDesignerIdAndId(designerId, productionProgressId)
        .orElseThrow(() -> new CMCException(PRODUCTION_PROGRESS_ILLEGAL_ID));
    }

    private static Map<ProgressType, String> retrieveProductionProgressAllImg(ProductionProgress productionProgress) {
        return productionProgress.getImgs().stream()
                .collect(Collectors.toMap(ProductionProgressImg::getType, ProductionProgressImg::getUrl));
    }
}
