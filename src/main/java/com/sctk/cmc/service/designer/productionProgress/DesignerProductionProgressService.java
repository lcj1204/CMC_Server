package com.sctk.cmc.service.designer.productionProgress;

import com.sctk.cmc.service.designer.productionProgress.dto.DesignerProductionProgressGetDetailResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.DesignerProductionProgressGetInfoResponse;
import com.sctk.cmc.service.designer.productionProgress.dto.DesignerProductionProgressIdResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DesignerProductionProgressService {

    List<DesignerProductionProgressGetInfoResponse> retrieveAllInfo(Long designerId);

    DesignerProductionProgressGetDetailResponse retrieveDetailById(Long designerId, Long productionProgressId);

    DesignerProductionProgressIdResponse registerProductionProgressImage(Long designerId, Long productionProgressId, String progressType, List<MultipartFile> multipartFileList);
}
