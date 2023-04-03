package com.sctk.cmc.service;

import com.sctk.cmc.dto.member.MemberJoinParam;
import com.sctk.cmc.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MemberServiceImplTest {
    @MockBean
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    public void member_회원가입_테스트() throws Exception {
        //given
        when(memberRepository.save(any())).thenReturn(1L);
        MemberJoinParam param = new MemberJoinParam("nameA", "nicknameA", "emailA", "passwordA");

        //when
        Long joinedMemberId = memberService.join(param);

        //then
        assertThat(joinedMemberId).isEqualTo(1L);
    }
}