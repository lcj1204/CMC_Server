package com.sctk.cmc.service;

import com.sctk.cmc.common.exception.ResponseStatus;
import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.service.abstractions.AuthService;
import com.sctk.cmc.service.abstractions.DesignerService;
import com.sctk.cmc.service.abstractions.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final MemberService memberService;
    private final DesignerService designerService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member authenticateMember(String email, String password) {
        if (!memberService.existsByEmail(email)) {
            throw new CMCException(ResponseStatus.AUTHENTICATION_ILLEGAL_EMAIL);
        }

        Member member = memberService.retrieveByEmail(email);
        if (passwordEncoder.matches(password, member.getPassword())) {
            return member;
        }

        throw new CMCException(ResponseStatus.AUTHENTICATION_ILLEGAL_PASSWORD);
    }

    @Override
    public Designer authenticateDesigner(String email, String password) {
        if (!designerService.existsByEmail(email)) {
            throw new CMCException(ResponseStatus.AUTHENTICATION_ILLEGAL_EMAIL);
        }

        Designer designer = designerService.retrieveByEmail(email);
        if (passwordEncoder.matches(password, designer.getPassword())) {
            return designer;
        }

        throw new CMCException(ResponseStatus.AUTHENTICATION_ILLEGAL_PASSWORD);
    }
}
