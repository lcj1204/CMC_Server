package com.sctk.cmc.service.member.custom;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.controller.designer.custom.dto.CustomIdResponse;
import com.sctk.cmc.domain.*;
import com.sctk.cmc.repository.designer.DesignerRepository;
import com.sctk.cmc.repository.member.custom.MemberCustomRepository;
import com.sctk.cmc.repository.member.MemberRepository;
import com.sctk.cmc.service.member.custom.dto.CustomRegisterParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.sctk.cmc.common.exception.ResponseStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberCustomServiceImpl implements MemberCustomService {
    private final MemberCustomRepository memberCustomRepository;
    private final MemberRepository memberRepository;
    private final DesignerRepository designerRepository;

    @Override
    public CustomIdResponse register(Long memberId, CustomRegisterParams customRegisterParams) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CMCException(MEMBERS_ILLEGAL_ID));

        Designer designer = designerRepository.findById(customRegisterParams.getDesignerId())
                .orElseThrow(() -> new CMCException(DESIGNERS_ILLEGAL_ID));

        Custom createdcCustom = Custom.create(member, designer, customRegisterParams);

        Custom saveCustom = memberCustomRepository.save(createdcCustom);

        return CustomIdResponse.of(saveCustom.getId());
    }

    @Override
    public Custom retrieveById(Long customId) {
        return memberCustomRepository.findWithMemberById(customId)
                .orElseThrow(() -> new CMCException(CUSTOM_ILLEGAL_ID));
    }
}
