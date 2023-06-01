package com.sctk.cmc.service.member.custom;

import com.sctk.cmc.controller.designer.custom.dto.CustomIdResponse;
import com.sctk.cmc.domain.Custom;
import com.sctk.cmc.service.member.custom.dto.CustomRegisterParams;

public interface MemberCustomService {
    CustomIdResponse register(Long memberId, CustomRegisterParams customRegisterParams);

    Custom retrieveById(Long customId);
}
