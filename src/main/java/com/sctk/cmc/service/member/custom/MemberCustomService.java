package com.sctk.cmc.service.member.custom;

import com.sctk.cmc.controller.designer.custom.dto.CustomIdResponse;
import com.sctk.cmc.controller.member.custom.dto.MemberCustomGetDetailResponse;
import com.sctk.cmc.controller.member.custom.dto.MemberCustomGetInfoResponse;
import com.sctk.cmc.controller.member.custom.dto.MemberCustomResultGetAcceptanceResponse;
import com.sctk.cmc.controller.member.custom.dto.MemberCustomResultGetRejectionResponse;
import com.sctk.cmc.domain.Custom;
import com.sctk.cmc.service.member.custom.dto.CustomRegisterParams;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberCustomService {
    CustomIdResponse register(Long memberId, CustomRegisterParams customRegisterParams, List<MultipartFile> multipartFiles);

    List<MemberCustomGetInfoResponse> retrieveAllInfo(Long memberId);

    MemberCustomGetDetailResponse retrieveDetailById(Long memberId, Long customId);

    MemberCustomResultGetAcceptanceResponse retrieveAcceptance(Long memberId, Long customId, Long customResultId);

    MemberCustomResultGetRejectionResponse retrieveRejection(Long memberId, Long customId, Long customResultId);

    Custom retrieveWtihMember(Long customId);

    Custom retrieveWithMemberAndImgs(Long customId);

    Custom retrieveWithImgsByMemberId(Long memberId, Long customId);

    Custom retrieveByMemberId(Long memberId, Long customId);
}
