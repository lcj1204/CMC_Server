package com.sctk.cmc.service;

import com.sctk.cmc.domain.Member;
import com.sctk.cmc.exception.CMCException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.sctk.cmc.exception.ResponseStatus.*;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member authenticate(String email, String password) {
        if (!memberService.existsByEmail(email)) {
            throw new CMCException(MEMBERS_ILLEGAL_EMAIL);
        }

        Member member = memberService.retrieveByEmail(email);
        if (passwordEncoder.matches(password, member.getPassword())) {
            return member;
        }

        throw new CMCException(MEMBERS_ILLEGAL_PASSWORD);
    }
}
