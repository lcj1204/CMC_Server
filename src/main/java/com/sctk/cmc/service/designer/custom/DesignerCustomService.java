package com.sctk.cmc.service.designer.custom;

import com.sctk.cmc.controller.designer.custom.dto.CustomGetDetailResponse;
import com.sctk.cmc.controller.designer.custom.dto.CustomGetInfoResponse;
import com.sctk.cmc.controller.designer.custom.dto.CustomIdResponse;
import com.sctk.cmc.service.designer.custom.dto.CustomResultAcceptParams;
import com.sctk.cmc.service.designer.custom.dto.CustomResultRejectParams;

import java.util.List;

public interface DesignerCustomService {

    List<CustomGetInfoResponse> retrieveAllInfo(Long designerId);

    CustomGetDetailResponse retrieveDetailById(Long designerId, Long customId);

    CustomIdResponse deleteSoft(Long designerId, Long customId);

    CustomIdResponse acceptCustom(Long designerId, Long customId, CustomResultAcceptParams customResultAcceptParams);

    CustomIdResponse rejectCustom(Long designerId, Long customId, CustomResultRejectParams customResultRejectParams);
}
