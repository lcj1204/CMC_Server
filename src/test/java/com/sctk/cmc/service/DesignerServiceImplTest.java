package com.sctk.cmc.service;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.dto.designer.DesignerJoinParam;
import com.sctk.cmc.exception.CMCException;
import com.sctk.cmc.repository.DesignerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.sctk.cmc.exception.ResponseStatus.AUTHENTICATION_ILLEGAL_EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class DesignerServiceImplTest {
    @MockBean
    DesignerRepository designerRepository;
    @Autowired
    DesignerService designerService;

    @Test
    public void designer_회원가입_테스트() throws Exception {
        //given
        when(designerRepository.save(any())).thenReturn(1L);
        DesignerJoinParam param = new DesignerJoinParam("nameA", "nicknameA", "emailA", "passwordA");

        //when
        Long joinedMemberId = designerService.join(param);

        //then
        assertThat(joinedMemberId).isEqualTo(1L);
    }

    @Test
    public void designer_존재여부_존재O_테스트() throws Exception {
        when(designerRepository.findByEmail(any())).thenReturn(Optional.of(createDesigner()));

        boolean exists = designerService.existsByEmail("anyEmail");

        assertThat(exists).isTrue();
    }

    @Test
    public void designer_존재여부_존재X_테스트() throws Exception {
        when(designerRepository.findByEmail(any())).thenReturn(Optional.empty());

        boolean exists = designerService.existsByEmail("anyEmail");

        assertThat(exists).isFalse();
    }

    @Test
    public void email로_designer_검색_존재O_테스트() throws Exception {
        //given
        String anyName = "anyName";
        String anyEmail = "anyEmail";
        when(designerRepository.findByEmail(any())).thenReturn(Optional.of(createDesignerHasNameEmail(anyName, anyEmail)));

        //when
        Designer designer = designerService.retrieveByEmail(anyEmail);

        //then
        assertThat(designer.getEmail()).isEqualTo(anyEmail);
    }

    @Test
    public void email로_designer_검색_존재X_테스트() throws Exception {
        //given
        String anyEmail = "anyEmail";
        when(designerRepository.findByEmail(any())).thenReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> designerService.retrieveByEmail(anyEmail))
                .isInstanceOf(CMCException.class)
                .hasMessage(AUTHENTICATION_ILLEGAL_EMAIL.name());
    }

    Designer createDesigner() {
        return Designer.builder().build();
    }

    Designer createDesignerHasNameEmail(String name, String email) {
        return Designer.builder()
                .name(name)
                .email(email)
                .build();
    }
}