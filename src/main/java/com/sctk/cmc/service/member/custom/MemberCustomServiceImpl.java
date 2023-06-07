package com.sctk.cmc.service.member.custom;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.controller.designer.custom.dto.CustomIdResponse;
import com.sctk.cmc.controller.member.custom.dto.MemberCustomGetDetailResponse;
import com.sctk.cmc.controller.member.custom.dto.MemberCustomGetInfoResponse;
import com.sctk.cmc.controller.member.custom.dto.MemberCustomResultGetAcceptanceResponse;
import com.sctk.cmc.controller.member.custom.dto.MemberCustomResultGetRejectionResponse;
import com.sctk.cmc.domain.*;
import com.sctk.cmc.repository.designer.DesignerRepository;
import com.sctk.cmc.repository.member.MemberRepository;
import com.sctk.cmc.repository.member.custom.MemberCustomRepository;
import com.sctk.cmc.service.member.custom.dto.CustomRegisterParams;
import com.sctk.cmc.util.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.sctk.cmc.common.exception.ResponseStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberCustomServiceImpl implements MemberCustomService {
    private final MemberCustomRepository memberCustomRepository;
    private final MemberRepository memberRepository;
    private final DesignerRepository designerRepository;
    private final AmazonS3Service amazonS3Service;

    @Override
    @Transactional
    public CustomIdResponse register(Long memberId, CustomRegisterParams customRegisterParams, List<MultipartFile> multipartFiles) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CMCException(MEMBERS_ILLEGAL_ID));

        Designer designer = designerRepository.findById(customRegisterParams.getDesignerId())
                .orElseThrow(() -> new CMCException(DESIGNERS_ILLEGAL_ID));

        Custom createdcCustom = Custom.create(member, designer, customRegisterParams);
        CustomReference createdCustomReference = CustomReference.create(createdcCustom);

        Custom saveCustom = memberCustomRepository.save(createdcCustom);

        List<String> uploadUrls = amazonS3Service.uploadCustomImgs(multipartFiles, memberId, saveCustom.getId());
        uploadUrls.forEach(u -> new CustomReferenceImg(u, createdCustomReference));

        return CustomIdResponse.of(saveCustom.getId());
    }

    @Override
    public List<MemberCustomGetInfoResponse> retrieveAllInfo(Long memberId) {

        List<Custom> allCustoms = memberCustomRepository.findAllByMemberId(memberId);

        List<MemberCustomGetInfoResponse> responseList = allCustoms.stream()
                .map(c -> {
                    // 썸네일 이미지는 첫번째 사진으로 함.
                    String ThumbnailImgUrl = c.getReference().getReferenceImgs().get(0).getUrl();
                    return MemberCustomGetInfoResponse.of(c, ThumbnailImgUrl);
                })
                .collect(Collectors.toList());

        return responseList;
    }

    @Override
    public MemberCustomGetDetailResponse retrieveDetailById(Long memberId, Long customId) {

        Custom custom = retrieveWithImgsByMemberId(memberId, customId);

        return MemberCustomGetDetailResponse.of(custom);
    }

    @Override
    public MemberCustomResultGetAcceptanceResponse retrieveAcceptance(Long memberId, Long customId, Long customResultId) {

        Custom custom = retrieveByMemberId(memberId, customId);

        validateCustomResultStatus(custom, customResultId, CustomStatus.APPROVAL);

        return MemberCustomResultGetAcceptanceResponse.of(custom.getCustomResult());
    }

    @Override
    public MemberCustomResultGetRejectionResponse retrieveRejection(Long memberId, Long customId, Long customResultId) {

        Custom custom = retrieveByMemberId(memberId, customId);

        validateCustomResultStatus(custom, customResultId, CustomStatus.REFUSAL);

        return MemberCustomResultGetRejectionResponse.of(custom.getCustomResult());
    }

    @Override
    public Custom retrieveWtihMember(Long customId) {
        return memberCustomRepository.findWithMemberById(customId)
                .orElseThrow(() -> new CMCException(CUSTOM_ILLEGAL_ID));
    }

    @Override
    public Custom retrieveWithMemberAndImgs(Long customId) {
        return memberCustomRepository.findWithMemberAndImgsById(customId)
                .orElseThrow(() -> new CMCException(CUSTOM_ILLEGAL_ID));
    }

    @Override
    public Custom retrieveWithImgsByMemberId(Long memberId, Long customId) {
        return memberCustomRepository.findWithImgsById(memberId, customId)
                .orElseThrow(() -> new CMCException(CUSTOM_ILLEGAL_ID));
    }

    @Override
    public Custom retrieveByMemberId(Long memberId, Long customId) {
        return memberCustomRepository.findById(memberId, customId)
                .orElseThrow(() -> new CMCException(CUSTOM_ILLEGAL_ID));
    }

    private void validateCustomResultStatus(Custom custom, Long customResultId, CustomStatus status) {
        if (custom.getAccepted() != status) {
            throw new CMCException(NOT_APPROVAL_CUSTOM_RESULT);
        }else if (custom.getCustomResult() == null) {
            throw new CMCException(CUSTOM_RESULT_ILLEGAL_ID);
        } else if (!custom.getCustomResult().getId().equals(customResultId)) {
            throw new CMCException(CUSTOM_RESULT_UNMATCH_ID);
        }
    }
}
