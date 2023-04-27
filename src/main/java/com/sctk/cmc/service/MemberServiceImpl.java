package com.sctk.cmc.service;

import com.sctk.cmc.domain.BodyInfo;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.service.dto.BodyInfoParams;
import com.sctk.cmc.service.dto.member.MemberDetails;
import com.sctk.cmc.service.dto.member.MemberJoinParam;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.repository.MemberRepository;
import com.sctk.cmc.service.abstractions.MemberService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sctk.cmc.common.exception.ResponseStatus.*;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long join(MemberJoinParam param) {
        return memberRepository.save(Member.builder()
                .name(param.getName())
                .nickname(param.getNickname())
                .email(param.getEmail())
                .password(passwordEncoder.encode(param.getPassword()))
                .build());
    }

    @Override
    public MemberDetails retrieveById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CMCException(MEMBERS_ILLEGAL_ID));

        return new MemberDetails(
                member.getName(),
                member.getNickname(),
                member.getEmail(),
                member.getProfileImgUrl(),
                member.getIntroduce()
        );
    }

    @Override
    public Member retrieveByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CMCException(AUTHENTICATION_ILLEGAL_EMAIL));
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email)
                .isPresent();
    }

    @Transactional
    @Override
    public void registerBodyInfo(Long memberId, BodyInfoParams bodyInfoParams) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CMCException(MEMBERS_ILLEGAL_ID));

        BodyInfo bodyInfo = BodyInfo.builder()
                .member(member)
                .height(bodyInfoParams.getHeight())
                .hip(bodyInfoParams.getHip())
                .lower(bodyInfoParams.getLower())
                .upper(bodyInfoParams.getUpper())
                .waist(bodyInfoParams.getWaist())
                .chest(bodyInfoParams.getChest())
                .thigh(bodyInfoParams.getThigh())
                .weight(bodyInfoParams.getWeight())
                .shoulder(bodyInfoParams.getShoulder())
                .build();
    }
}
