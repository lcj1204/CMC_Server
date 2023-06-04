package com.sctk.cmc.service.member.custom;

import com.sctk.cmc.controller.designer.custom.dto.CustomIdResponse;
import com.sctk.cmc.controller.member.custom.dto.MemberCustomGetInfoResponse;
import com.sctk.cmc.domain.Custom;
import com.sctk.cmc.service.member.custom.dto.CustomRegisterParams;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberCustomService {
    CustomIdResponse register(Long memberId, CustomRegisterParams customRegisterParams, List<MultipartFile> multipartFiles);

    List<MemberCustomGetInfoResponse> retrieveAllInfo(Long memberId);

    Custom retrieveById(Long customId);

    Custom retrieveWithImgsById(Long customId);
}
