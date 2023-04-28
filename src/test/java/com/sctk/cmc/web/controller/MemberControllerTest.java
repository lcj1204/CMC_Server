package com.sctk.cmc.web.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.abstractions.MemberService;
import com.sctk.cmc.service.dto.member.MemberDetails;
import com.sctk.cmc.web.dto.MemberDetailsResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
// MvcResult 한글 깨짐
@Import(HttpEncodingAutoConfiguration.class)
class MemberControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    MemberService memberService;

    ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setup() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(1L, "", List.of(new SimpleGrantedAuthority("MEMBER"))));
    }

    @Test
    public void member_details_조회_테스트() throws Exception {
        // given
        MemberDetails mockDetails = new MemberDetails(
                "name",
                "nickname",
                "email",
                "profileImgUrl",
                "introduce"
        );

        MemberDetailsResponse expectedResponse = new MemberDetailsResponse(
                mockDetails.getName(),
                mockDetails.getNickname(),
                mockDetails.getEmail(),
                mockDetails.getProfileImgUrl(),
                mockDetails.getIntroduce()
        );

        when(memberService.retrieveById(anyLong())).thenReturn(mockDetails);

        // when
        MvcResult result = mvc.perform(get(REQUEST_URI.MEMBER_DETAILS))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
        JavaType javaType = om.getTypeFactory().constructParametricType(BaseResponse.class, MemberDetailsResponse.class);
        BaseResponse<MemberDetailsResponse> baseResponse = om.readValue(result.getResponse().getContentAsString(), javaType);
        MemberDetailsResponse response = baseResponse.getData();

        assertThat(response).isEqualTo(expectedResponse);
    }

    static class REQUEST_URI {
        static String URI_PREFIX = "/api/v1";
        static String BASE = "/member";
        static String MEMBER_DETAILS = URI_PREFIX + BASE + "/detail";
    }
}