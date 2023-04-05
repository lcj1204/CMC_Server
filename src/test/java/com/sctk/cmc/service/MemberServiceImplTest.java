package com.sctk.cmc.service;

import com.sctk.cmc.domain.Member;
import com.sctk.cmc.dto.member.MemberJoinParam;
import com.sctk.cmc.exception.CMCException;
import com.sctk.cmc.exception.ResponseStatus;
import com.sctk.cmc.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.sctk.cmc.exception.ResponseStatus.*;
import static org.assertj.core.api.Assertions.*;
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

    @Test
    public void member_존재여부_존재O_테스트() throws Exception {
        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(createMember()));

        boolean exists = memberService.existsByEmail("anyEmail");

        assertThat(exists).isTrue();
    }

    @Test
    public void member_존재여부_존재X_테스트() throws Exception {
        when(memberRepository.findByEmail(any())).thenReturn(Optional.empty());

        boolean exists = memberService.existsByEmail("anyEmail");

        assertThat(exists).isFalse();
    }

    @Test
    public void email로_member_검색_존재O_테스트() throws Exception {
        //given
        String anyEmail = "anyEmail";
        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(createMemberHasEmail(anyEmail)));

        //when
        Member member = memberService.retrieveByEmail(anyEmail);

        //then
        assertThat(member.getEmail()).isEqualTo(anyEmail);
    }

    @Test
    public void email로_member_검색_존재X_테스트() throws Exception {
        //given
        String anyEmail = "anyEmail";
        when(memberRepository.findByEmail(any())).thenReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> memberService.retrieveByEmail(anyEmail))
                .isInstanceOf(CMCException.class)
                .hasMessage(AUTHENTICATION_ILLEGAL_EMAIL.name());
    }

    Member createMember() {
        return Member.builder().build();
    }

    Member createMemberHasEmail(String email) {
        return Member.builder()
                .email(email)
                .build();
    }
}