package com.sctk.cmc.service;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.auth.AuthService;
import com.sctk.cmc.service.designer.DesignerService;
import com.sctk.cmc.service.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.sctk.cmc.common.exception.ResponseStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthServiceImplTest {
    @Autowired
    AuthService authService;

    @MockBean
    MemberService memberService;
    @MockBean
    DesignerService designerService;
    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    public void member_인증_테스트() throws Exception {
        //given
        String anyEmail = "anyEmail";
        String anyPassword = "anyPassword";
        when(memberService.existsByEmail(any())).thenReturn(true);
        when(memberService.retrieveByEmail(any())).thenReturn(createMemberHasEmail(anyEmail));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        //when
        Member authenticatedMember = authService.authenticateMember(anyEmail, anyPassword);

        //then
        assertThat(authenticatedMember.getEmail()).isEqualTo(anyEmail);
    }

    @Test
    public void member_인증_존재X_이메일일때_테스트() throws Exception {
        //given
        String email = "email";
        String password = "password";
        when(memberService.existsByEmail(any())).thenReturn(false);

        // then
        assertThatThrownBy(() -> authService.authenticateMember(email, password))
                .isInstanceOf(CMCException.class)
                .hasMessage(AUTHENTICATION_ILLEGAL_EMAIL.name());
    }

    Member createMemberHasEmail(String email) {
        return Member.builder()
                .email(email)
                .build();
    }

    @Test
    public void designer_인증_테스트() throws Exception {
        //given
        String anyName = "anyName";
        String anyEmail = "anyEmail";
        String anyPassword = "anyPassword";
        when(designerService.existsByEmail(any())).thenReturn(true);
        when(designerService.retrieveByEmail(any())).thenReturn(createDesignerHasNameEmail(anyName, anyEmail));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        //when
        Designer authenticatedDesigner = authService.authenticateDesigner(anyEmail, anyPassword);

        //then
        assertThat(authenticatedDesigner.getEmail()).isEqualTo(anyEmail);
    }

    @Test
    public void designer_인증_존재X_이메일일때_테스트() throws Exception {
        //given
        String email = "email";
        String password = "password";
        when(designerService.existsByEmail(any())).thenReturn(false);

        // then
        assertThatThrownBy(() -> authService.authenticateDesigner(email, password))
                .isInstanceOf(CMCException.class)
                .hasMessage(AUTHENTICATION_ILLEGAL_EMAIL.name());
    }


    Designer createDesignerHasNameEmail(String name, String email) {
        return Designer.builder()
                .name(name)
                .email(email)
                .build();
    }
}