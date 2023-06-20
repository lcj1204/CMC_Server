package com.sctk.cmc.service.designer.productionProgress;

import com.sctk.cmc.domain.ProgressType;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressGetDetailResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressGetInfoResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressIdResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductionProgressService {

    // 제작중 전체 간단 조회
    List<ProductionProgressGetInfoResponse> retrieveAllInfo(Long designerId);

    // 제작중 상세 조회
    ProductionProgressGetDetailResponse retrieveDetailById(Long designerId, Long productionProgressId);

    // 이미지 추가
    ProductionProgressIdResponse registerProductionProgressImage(Long designerId, Long productionProgressId, MultipartFile multipartFile, ProgressType type);
}
