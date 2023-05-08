package com.sctk.cmc.auth.service;

import com.sctk.cmc.auth.domain.SecurityDesignerDetails;
import com.sctk.cmc.auth.domain.SecurityMemberDetails;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.repository.DesignerRepository;
import com.sctk.cmc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sctk.cmc.common.exception.ResponseStatus.MEMBERS_ILLEGAL_ID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SecurityUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final DesignerRepository designerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadMemberByUserEmail(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CMCException(MEMBERS_ILLEGAL_ID));

        log.info("loadMemberByUserEmail, member=[{}][{}][{}]", member.getId(), member.getEmail(), member.getRole());
        return new SecurityMemberDetails(member);
    }

    public UserDetails loadDesignerByUserEmail(String email) throws UsernameNotFoundException {
        Designer designer = designerRepository.findByEmail(email)
                .orElseThrow(() -> new CMCException(MEMBERS_ILLEGAL_ID));

        log.info("loadDesignerByUserEmail, designer=[{}][{}][{}]", designer.getId(), designer.getEmail(), designer.getRole());
        return new SecurityDesignerDetails(designer);
    }
}
