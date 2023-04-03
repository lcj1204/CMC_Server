package com.sctk.cmc.repository;

import com.sctk.cmc.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void member_저장_테스트() throws Exception {
        Member member = createMember();

        Long returned = memberRepository.save(member);

        assertThat(member.getId()).isEqualTo(returned);
    }

    @Test
    public void member_email로_조회() throws Exception {
        //given
        String email = "email";
        Member member = createMemberWithEmail(email);
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByEmail(email)
                .orElse(null);

        //then
        assertThat(findMember).isNotNull();
        assertThat(findMember.getId().equals(member.getId())).isTrue();
        assertThat(findMember.getEmail().equals(member.getEmail())).isTrue();
    }

    public Member createMember() {
        return Member.builder()
                .build();
    }

    public Member createMemberWithEmail(String email) {
        return Member.builder()
                .email(email)
                .build();
    }
}