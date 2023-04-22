package com.sctk.cmc.service;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.HighCategory;
import com.sctk.cmc.domain.LowCategory;
import com.sctk.cmc.dto.designer.DesignerJoinParam;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.repository.DesignerRepository;
import com.sctk.cmc.service.abstractions.DesignerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.sctk.cmc.common.exception.ResponseStatus.*;
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
        DesignerJoinParam param = new DesignerJoinParam("nameA", "nicknameA", "emailA", "passwordA", "contactA");

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

    @Test
    public void name으로_designer_검색_존재O_테스트() throws Exception {
        //given
        String anyName = "anyName";
        String anyEmail = "anyEmail";

        List<Designer> mockFindDesigners = List.of(createDesignerHasNameEmail(anyName, anyEmail));

        when(designerRepository.findAllByName(any())).thenReturn(mockFindDesigners);

        //when
        List<Designer> designers = designerService.retrieveAllByName(anyName);

        //then
        assertThat(designers.size()).isEqualTo(1);
    }

    @Test
    public void name으로_designer_검색_여러명_존재_테스트() throws Exception {
        //given
        String anyName = "anyName";
        when(designerRepository.findAllByName(any())).thenReturn(Collections.EMPTY_LIST);

        //when
        List<Designer> designers = designerService.retrieveAllByName(anyName);

        //then
        assertThat(designers.size()).isEqualTo(0);
    }

    @Test
    public void name으로_designer_검색_존재X_테스트() throws Exception {
        //given
        String anyEmail = "anyEmail";
        when(designerRepository.findByEmail(any())).thenReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> designerService.retrieveByEmail(anyEmail))
                .isInstanceOf(CMCException.class)
                .hasMessage(AUTHENTICATION_ILLEGAL_EMAIL.name());
    }

    @Test
    public void 상위카테고리_등록_테스트() throws Exception {
        //given
        String designerName = "designer";
        String designerEmail = "email";
        Designer designer = createDesignerHasNameEmail(designerName, designerEmail);
        setId(designer, 1L);

        List<HighCategory> highCategories = List.of(
                new HighCategory(designer, "highCategory1"),
                new HighCategory(designer, "highCategory2"),
                new HighCategory(designer, "highCategory3")
        );


        when(designerRepository.findById(any())).thenReturn(Optional.of(designer));

        //when
        int registered = designerService.registerHighCategories(1L, highCategories);

        //then
        assertThat(registered).isEqualTo(highCategories.size());
    }

    @Test
    public void 상위카테고리_등록_3개_초과로_실패_테스트() throws Exception {
        //given
        String designerName = "designer";
        String designerEmail = "email";
        Designer designer = createDesignerHasNameEmail(designerName, designerEmail);
        setId(designer, 1L);

        List<HighCategory> highCategories = List.of(
                new HighCategory(designer, "highCategory1"),
                new HighCategory(designer, "highCategory2"),
                new HighCategory(designer, "highCategory3"),
                new HighCategory(designer, "highCategory4")
        );


        when(designerRepository.findById(any())).thenReturn(Optional.of(designer));

        //then
        assertThatThrownBy(() -> designerService.registerHighCategories(1L, highCategories))
                .isInstanceOf(CMCException.class)
                .hasMessage(DESIGNERS_HIGH_CATEGORY_MORE_THAN_LIMIT.name());
    }

    @Test
    public void 하위카테고리_등록_테스트() throws Exception {
        //given
        String designerName = "designer";
        String designerEmail = "email";
        Designer designer = createDesignerHasNameEmail(designerName, designerEmail);
        setId(designer, 1L);

        List<LowCategory> lowCategories = List.of(
                new LowCategory(designer, "lowCategory1"),
                new LowCategory(designer, "lowCategory2"),
                new LowCategory(designer, "lowCategory3")
        );


        when(designerRepository.findById(any())).thenReturn(Optional.of(designer));

        //when
        int registered = designerService.registerLowCategories(1L, lowCategories);

        //then
        assertThat(registered).isEqualTo(lowCategories.size());
    }

    @Test
    public void 하위카테고리_등록_3개_초과로_실패_테스트() throws Exception {
        //given
        String designerName = "designer";
        String designerEmail = "email";
        Designer designer = createDesignerHasNameEmail(designerName, designerEmail);
        setId(designer, 1L);

        List<LowCategory> lowCategories = List.of(
                new LowCategory(designer, "lowCategory1"),
                new LowCategory(designer, "lowCategory2"),
                new LowCategory(designer, "lowCategory3"),
                new LowCategory(designer, "lowCategory4")
        );


        when(designerRepository.findById(any())).thenReturn(Optional.of(designer));

        //then
        assertThatThrownBy(() -> designerService.registerLowCategories(1L, lowCategories))
                .isInstanceOf(CMCException.class)
                .hasMessage(DESIGNERS_LOW_CATEGORY_MORE_THAN_LIMIT.name());
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

    void setId(Designer designer, Long id) throws NoSuchFieldException, IllegalAccessException {
        Class<? extends Designer> designerClass = designer.getClass();
        Field idField = designerClass.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(designer, id);
    }
}