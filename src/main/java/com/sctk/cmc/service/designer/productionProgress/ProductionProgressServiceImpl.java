package com.sctk.cmc.service.designer.productionProgress;

import com.sctk.cmc.domain.ProductionProgress;
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
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductionProgressServiceImpl implements ProductionProgressService {
    private final ProductionProgressRepository productionProgressRepository;

    @Override
    public List<ProductionProgressGetInfoResponse> retrieveAllInfo(Long designerId) {
        List<ProductionProgress> allProductionProgress = productionProgressRepository.findAllByDesignerId(designerId);

        List<ProductionProgressGetInfoResponse> responseList = allProductionProgress.stream()
                .map(ProductionProgressGetInfoResponse::of)
                .collect(Collectors.toList());

        return responseList;
    }

    @Override
    public ProductionProgressGetDetailResponse retrieveDetailById(Long designerId, Long customId) {
        return null;
    }

    @Override
    public ProductionProgressIdResponse registerImage(Long designerId, Long customId, MultipartFile multipartFile, ProgressType type) {
        return null;
    }
}
