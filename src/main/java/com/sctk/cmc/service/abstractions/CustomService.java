package com.sctk.cmc.service.abstractions;

import com.sctk.cmc.service.dto.custom.CustomParams;
import com.sctk.cmc.service.dto.customResult.CustomResultAcceptParams;
import com.sctk.cmc.service.dto.customResult.CustomResultRejectParams;
import com.sctk.cmc.web.dto.custom.CustomGetDetailResponse;
import com.sctk.cmc.web.dto.custom.CustomGetInfoResponse;
import com.sctk.cmc.web.dto.custom.CustomIdResponse;
import com.sctk.cmc.web.dto.custom.CustomResultIdResponse;

import java.util.List;

public interface CustomService {
    CustomIdResponse register(Long memberId, CustomParams customParams);

    List<CustomGetInfoResponse> retrieveAllInfo(Long designerId);

    CustomGetDetailResponse retrieveDetailById(Long designerId, Long customId);

    CustomGetInfoResponse retrieveInfoById(Long designerId, Long customId);

    CustomIdResponse deleteSoft(Long designerId, Long customId);

    CustomResultIdResponse acceptCustom(Long designerId, Long customId, CustomResultAcceptParams customResultAcceptParams);

    CustomResultIdResponse rejectCustom(Long designerId, Long customId, CustomResultRejectParams customResultRejectParams);
}
