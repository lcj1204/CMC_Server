package com.sctk.cmc.service;

import com.sctk.cmc.domain.BodyInfo;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.service.dto.BodyInfoParams;
import com.sctk.cmc.service.dto.member.BodyInfoView;
import com.sctk.cmc.service.dto.member.MemberDetails;
import com.sctk.cmc.service.dto.member.MemberInfo;
import com.sctk.cmc.service.dto.member.MemberJoinParam;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.repository.MemberRepository;
import com.sctk.cmc.service.abstractions.MemberService;
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

    public Member retrieveById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CMCException(MEMBERS_ILLEGAL_ID));
    }

    @Override
    public MemberDetails retrieveDetailsById(Long memberId) {
        Member member = retrieveById(memberId);

        return new MemberDetails(
                member.getName(),
                member.getNickname(),
                member.getEmail(),
                member.getProfileImgUrl(),
                member.getIntroduce()
        );
    }

    @Override
    public MemberInfo retrieveInfoById(Long memberId) {
        Member member = retrieveById(memberId);
        BodyInfo info = member.getBodyInfo();

        return new MemberInfo(
                member.getName(),
                member.getProfileImgUrl(),
                BodyInfoView.builder()
                        .height(info.getHeight())
                        .weight(info.getWeight())
                        .shoulder(info.getShoulder())
                        .chest(info.getChest())
                        .waist(info.getWaist())
                        .hip(info.getHip())
                        .thigh(info.getThigh())
                        .upper(info.getUpper())
                        .lower(info.getLower())
                        .build()
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

        BodyInfo.builder()
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
