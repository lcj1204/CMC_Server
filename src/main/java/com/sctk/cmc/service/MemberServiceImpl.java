package com.sctk.cmc.service;

import com.sctk.cmc.domain.Member;
import com.sctk.cmc.dto.member.MemberJoinParam;
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

    @Override
    public Member retrieveById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CMCException(MEMBERS_ILLEGAL_ID));
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
}
