package com.sctk.cmc.service;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.domain.Custom;
import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.repository.CustomRepository;
import com.sctk.cmc.repository.DesignerRepository;
import com.sctk.cmc.repository.MemberRepository;
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
import java.util.stream.Collectors;

import static com.sctk.cmc.common.exception.ResponseStatus.DESIGNERS_ILLEGAL_ID;
import static com.sctk.cmc.common.exception.ResponseStatus.MEMBERS_ILLEGAL_ID;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CustomServiceImpl implements CustomService {
    private final CustomRepository customRepository;
    private final MemberRepository memberRepository;
    private final DesignerRepository designerRepository;

    @Override
    public CustomIdResponse register(Long memberId, CustomParams customParams) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CMCException(MEMBERS_ILLEGAL_ID));

        Designer designer = designerRepository.findById(customParams.getDesignerId())
                .orElseThrow(() -> new CMCException(DESIGNERS_ILLEGAL_ID));

        Custom createdcCustom = Custom.create(member, designer, customParams);

        Custom saveCustom = customRepository.save(createdcCustom);

        return CustomIdResponse.of(saveCustom.getId());
    }

    @Override
    public List<CustomGetInfoResponse> retrieveAllInfo(Long designerId) {

        List<Custom> allCustoms = customRepository.findAllByDesignerId(designerId);

        List<CustomGetInfoResponse> responseList = allCustoms.stream()
                .map(CustomGetInfoResponse::of)
                .collect(Collectors.toList());

        return responseList;
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
