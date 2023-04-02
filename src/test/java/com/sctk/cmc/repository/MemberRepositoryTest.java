package com.sctk.cmc.repository;

import com.sctk.cmc.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void member_저장_테스트() throws Exception {
        Member member = createMember();

        memberRepository.save(member);

        assertThat(member.getId()).isEqualTo(1L);
    }

    public Member createMember() {
        return Member.builder()
                .build();
    }
}