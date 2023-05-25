package com.sctk.cmc.service;

import com.sctk.cmc.service.abstractions.CustomService;
import com.sctk.cmc.service.dto.custom.CustomParams;
import com.sctk.cmc.service.dto.customResult.CustomResultAcceptParams;
import com.sctk.cmc.service.dto.customResult.CustomResultRejectParams;
import com.sctk.cmc.web.dto.custom.CustomGetDetailResponse;
import com.sctk.cmc.web.dto.custom.CustomGetInfoResponse;
import com.sctk.cmc.web.dto.custom.CustomIdResponse;
import com.sctk.cmc.web.dto.custom.CustomResultIdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CustomServiceImpl implements CustomService {
    @Override
    public CustomIdResponse register(Long memberId, CustomParams customParams) {
        return null;
    }

    @Override
    public List<CustomGetInfoResponse> retrieveAllInfo(Long designerId) {
        return null;
    }

    @Override
    public CustomGetDetailResponse retrieveDetailById(Long designerId, Long customId) {
        return null;
    }

    @Override
    public CustomGetInfoResponse retrieveInfoById(Long designerId, Long customId) {
        return null;
    }

    @Override
    public CustomIdResponse deleteSoft(Long designerId, Long customId) {
        return null;
    }

    @Override
    public CustomResultIdResponse acceptCustom(Long designerId, Long customId, CustomResultAcceptParams customResultAcceptParams) {
        return null;
    }

    @Override
    public CustomResultIdResponse rejectCustom(Long designerId, Long customId, CustomResultRejectParams customResultRejectParams) {
        return null;
    }
}
