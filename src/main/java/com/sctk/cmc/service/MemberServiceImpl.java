package com.sctk.cmc.service;

import com.sctk.cmc.domain.Member;
import com.sctk.cmc.dto.member.MemberJoinParam;
import com.sctk.cmc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Long join(MemberJoinParam param) {
        return memberRepository.save(Member.builder()
                .name(param.getName())
                .nickname(param.getNickname())
                .email(param.getEmail())
                .password(param.getPassword())
                .build());
    }

    @Override
    public Member retrieveByEmail(String email) {
        return null;
    }
}
