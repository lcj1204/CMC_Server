package com.sctk.cmc.service;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.domain.*;
import com.sctk.cmc.repository.CustomRepository;
import com.sctk.cmc.repository.DesignerRepository;
import com.sctk.cmc.repository.MemberRepository;
import com.sctk.cmc.service.abstractions.CustomService;
import com.sctk.cmc.service.dto.custom.CustomRegisterParams;
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

import static com.sctk.cmc.common.exception.ResponseStatus.*;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CustomServiceImpl implements CustomService {
    private final CustomRepository customRepository;
    private final MemberRepository memberRepository;
    private final DesignerRepository designerRepository;

    @Override
    public CustomIdResponse register(Long memberId, CustomRegisterParams customRegisterParams) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CMCException(MEMBERS_ILLEGAL_ID));

        Designer designer = designerRepository.findById(customRegisterParams.getDesignerId())
                .orElseThrow(() -> new CMCException(DESIGNERS_ILLEGAL_ID));

        Custom createdcCustom = Custom.create(member, designer, customRegisterParams);

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

        Custom custom = customRepository.findWithMemberById(customId)
                .orElseThrow(() -> new CMCException(CUSTOM_ILLEGAL_ID));

        //해당 커스텀 요청이 로그인한 디자이너 소유인지 검증
        validateDesignerAuthority(designerId, custom);

        return CustomGetDetailResponse.of(custom);
    }

    @Override
    public CustomGetInfoResponse retrieveInfoById(Long designerId, Long customId) {

        Custom custom = customRepository.findWithMemberById(customId)
                .orElseThrow(() -> new CMCException(CUSTOM_ILLEGAL_ID));

        //해당 커스텀 요청이 로그인한 디자이너 소유인지 검증
        validateDesignerAuthority(designerId, custom);

        return CustomGetInfoResponse.of(custom);
    }

    @Transactional
    @Override
    public CustomIdResponse deleteSoft(Long designerId, Long customId) {

        Custom custom = customRepository.findWithMemberById(customId)
                .orElseThrow(() -> new CMCException(CUSTOM_ILLEGAL_ID));

        validateDesignerAuthority(designerId, custom);

        custom.changeActiveToFalse();

        return CustomIdResponse.of(custom.getId());
    }

    @Transactional
    @Override
    public CustomResultIdResponse acceptCustom(Long designerId, Long customId,
                                               CustomResultAcceptParams customResultAcceptParams) {

        Custom custom = customRepository.findWithMemberById(customId)
                .orElseThrow(() -> new CMCException(CUSTOM_ILLEGAL_ID));

        validateDesignerAuthority(designerId, custom);

        // accepted 가 REQUESTING 인지 검증
        validateAcceptedIsRequesting(custom);

        CustomResult.ofAcceptance(custom, customResultAcceptParams);
        custom.changeStatusTo(CustomStatus.APPROVAL);

        return CustomResultIdResponse.of(custom.getId());
    }

    @Transactional
    @Override
    public CustomResultIdResponse rejectCustom(Long designerId, Long customId, CustomResultRejectParams customResultRejectParams) {

        Custom custom = customRepository.findWithMemberById(customId)
                .orElseThrow(() -> new CMCException(CUSTOM_ILLEGAL_ID));

        validateDesignerAuthority(designerId, custom);

        validateAcceptedIsRequesting(custom);

        CustomResult.ofRejection(custom, customResultRejectParams);
        custom.changeStatusTo(CustomStatus.REFUSAL);

        return CustomResultIdResponse.of(custom.getId());
    }

    private void validateDesignerAuthority(Long designerId, Custom custom) {
        if ( !custom.getDesigner().getId().equals(designerId) ) {
            throw new CMCException(NOT_HAVE_DESIGNERS_AUTHORITY);
        }
    }

    private void validateAcceptedIsRequesting(Custom custom) {
        if (custom.getAccepted() != CustomStatus.REQUESTING) {
            throw new CMCException(ALREADY_RESPONDED_CUSTOM);
        }
    }

}
