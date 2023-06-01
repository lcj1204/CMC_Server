package com.sctk.cmc.web.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sctk.cmc.auth.domain.SecurityMemberDetails;
import com.sctk.cmc.common.exception.ResponseStatus;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.domain.SizesByPart;
import com.sctk.cmc.service.member.MemberService;
import com.sctk.cmc.service.member.dto.BodyInfoView;
import com.sctk.cmc.service.member.dto.MemberDetail;
import com.sctk.cmc.service.member.dto.MemberInfo;
import com.sctk.cmc.controller.member.dto.BodyInfoPostRequest;
import com.sctk.cmc.controller.member.dto.MemberDetailResponse;
import com.sctk.cmc.controller.member.dto.MemberInfoResponse;
import com.sctk.cmc.controller.member.MemberController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.lang.reflect.Field;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    static Long mockMemberId = 1L;
    ObjectMapper om = new ObjectMapper();
    UserDetails mockMemberDetails = getMockMemberDetails();

    @BeforeEach
    void setup() {
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                mockMemberDetails,
                                null,
                                mockMemberDetails.getAuthorities()
                        )
                );
    }

    @Test
    public void member_detail_조회_테스트() throws Exception {
        // given
        MemberDetail mockDetail = new MemberDetail(
                "name",
                "nickname",
                "email",
                "profileImgUrl",
                "introduce"
        );

        MemberDetailResponse expectedResponse = new MemberDetailResponse(
                mockDetail.getName(),
                mockDetail.getNickname(),
                mockDetail.getEmail(),
                mockDetail.getProfileImgUrl(),
                mockDetail.getIntroduce()
        );

        when(memberService.retrieveDetailById(anyLong())).thenReturn(mockDetail);

        // when
        MvcResult result = mvc.perform(get(REQUEST_URI.MEMBER_DETAIL))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
        JavaType javaType = om.getTypeFactory().constructParametricType(BaseResponse.class, MemberDetailResponse.class);
        BaseResponse<MemberDetailResponse> baseResponse = om.readValue(result.getResponse().getContentAsString(), javaType);
        MemberDetailResponse response = baseResponse.getData();

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void member_info_조회_테스트() throws Exception {
        //given
        MemberInfo mockInfo = new MemberInfo(
                "name",
                "profileImgUrl",
                new BodyInfoView()
        );

        MemberInfoResponse expectedResponse = new MemberInfoResponse(
                mockInfo.getName(),
                mockInfo.getProfileImgUrl(),
                mockInfo.getBodyInfoView()
        );

        when(memberService.retrieveInfoById(anyLong())).thenReturn(mockInfo);

        //when
        MvcResult result = mvc.perform(get(REQUEST_URI.MEMBER_INFO))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        //then
        JavaType javaType = om.getTypeFactory().constructParametricType(BaseResponse.class, MemberInfoResponse.class);
        BaseResponse<MemberInfoResponse> response = om.readValue(result.getResponse().getContentAsString(), javaType);
        MemberInfoResponse infoResponse = response.getData();

        assertThat(infoResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void member_bodyInfo_등록_테스트() throws Exception {
        //given
        BodyInfoPostRequest request = new BodyInfoPostRequest(
                new SizesByPart(1, 2, 3, 4, 5, 6, 7, 8, 9)
        );

        //when
        MvcResult result = mvc.perform(post(REQUEST_URI.BODY_INFO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        //then
        JavaType javaType = om.getTypeFactory().constructParametricType(BaseResponse.class, ResponseStatus.class);
        BaseResponse<ResponseStatus> response = om.readValue(result.getResponse().getContentAsString(), javaType);

        assertThat(response.getCode()).isEqualTo(ResponseStatus.SUCCESS.getCode());
    }

    UserDetails getMockMemberDetails() {
        Member mockMember = Member.builder()
                .email("mockEmail")
                .build();

        // set id 1L
        try {
            Class<? extends Member> memberClass = mockMember.getClass();
            Field idField = memberClass.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(mockMember, mockMemberId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SecurityMemberDetails(mockMember);
    }

    static class REQUEST_URI {
        static String URI_PREFIX = "/api/v1";
        static String BASE = "/members";
        static String MEMBER_DETAIL = URI_PREFIX + BASE + "/detail";
        static String MEMBER_INFO = URI_PREFIX + BASE + "/" + mockMemberId + "/info";
        static String BODY_INFO = URI_PREFIX + BASE + "/body-info";
    }
}