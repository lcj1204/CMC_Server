package com.sctk.cmc.web.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.abstractions.DesignerService;
import com.sctk.cmc.service.dto.designer.DesignerInfo;
import com.sctk.cmc.web.dto.designer.DesignerInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DesignerController.class)
class DesignerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DesignerService designerService;

    private static final Long mockDesignerId = 1L;

    private final ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(mockDesignerId, "", List.of(new SimpleGrantedAuthority("DESIGNER"))));
    }

    @Test
    public void designer_info_조회_테스트() throws Exception {
        //given
        DesignerInfo mockInfo = new DesignerInfo(
                "name",
                "profileImgUrl",
                "introduce",
                1000,
                List.of("hc1", "hc2", "hc3"),
                List.of("lc1", "lc2", "lc3")
        );

        DesignerInfoResponse expectedResponse = new DesignerInfoResponse(
                mockInfo.getName(),
                mockInfo.getProfileImgUrl(),
                mockInfo.getIntroduce(),
                mockInfo.getLikes(),
                mockInfo.getHighCategoryNames(),
                mockInfo.getLowCategoryNames()
        );

        when(designerService.retrieveInfoById(anyLong())).thenReturn(mockInfo);

        //when
        MvcResult mvcResult = mvc.perform(get(REQUEST_URI.DESIGNER_INFO))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        //then
        JavaType javaType = om.getTypeFactory().constructParametricType(BaseResponse.class, DesignerInfoResponse.class);
        BaseResponse<DesignerInfoResponse> baseResponse = om.readValue(mvcResult.getResponse().getContentAsString(), javaType);
        DesignerInfoResponse response = baseResponse.getData();

        assertThat(response).isEqualTo(expectedResponse);
    }

    static class REQUEST_URI {
        static String URI_PREFIX = "/api/v1";
        static String BASE = "/designers";
        static String DESIGNER_INFO = URI_PREFIX + BASE + "/" + mockDesignerId + "/info";
    }
}