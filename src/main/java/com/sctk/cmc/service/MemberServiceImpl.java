package com.sctk.cmc.service;

import com.sctk.cmc.common.dto.member.MemberJoinParam;
import com.sctk.cmc.domain.BodyInfo;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.service.dto.member.*;
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

    private Member retrieveById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CMCException(MEMBERS_ILLEGAL_ID));
    }

    @Override
    public MemberDetail retrieveDetailById(Long memberId) {
        Member member = retrieveById(memberId);

        return new MemberDetail(
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
                new BodyInfoView(info.getSizes())
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
    public void registerBodyInfo(Long memberId, BodyInfoParams params) {
        Member member = retrieveById(memberId);

        new BodyInfo(member, params.getSizes());
    }

    @Transactional
    @Override
    public void modifyBodyInfo(Long memberId, BodyInfoModifyParams params) {
        Member member = retrieveById(memberId);

        new BodyInfo(member, params.getSizes());
    }
}
