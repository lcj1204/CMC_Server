package com.sctk.cmc.service;

import com.sctk.cmc.controller.designer.dto.CategoryParam;
import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.service.designer.dto.DesignerJoinParam;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.repository.designer.DesignerRepository;
import com.sctk.cmc.service.designer.DesignerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
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

        List<CategoryParam> categoryParams = createCategoryParams(3);

        when(designerRepository.findById(any())).thenReturn(Optional.of(designer));

        //when
        int registered = designerService.registerCategories(1L, categoryParams);

        //then
        assertThat(registered).isEqualTo(categoryParams.size());
    }

    @Test
    public void 상위카테고리_등록_3개_초과로_실패_테스트() throws Exception {
        //given
        String designerName = "designer";
        String designerEmail = "email";
        Designer designer = createDesignerHasNameEmail(designerName, designerEmail);
        setId(designer, 1L);

        List<CategoryParam> categoryParams = createCategoryParams(4);

        when(designerRepository.findById(any())).thenReturn(Optional.of(designer));

        //then
        assertThatThrownBy(() -> designerService.registerCategories(1L, categoryParams))
                .isInstanceOf(CMCException.class)
                .hasMessage(DESIGNERS_HIGH_CATEGORY_MORE_THAN_LIMIT.name());
    }

    @Test
    public void 하위카테고리_등록_3개_초과로_실패_테스트() throws Exception {
        //given
        String designerName = "designer";
        String designerEmail = "email";
        Designer designer = createDesignerHasNameEmail(designerName, designerEmail);
        setId(designer, 1L);

        List<CategoryParam> categoryParams = createCategoryParamsLowCategoryFlooded(3);

        when(designerRepository.findById(any())).thenReturn(Optional.of(designer));

        //then
        assertThatThrownBy(() -> designerService.registerCategories(1L, categoryParams))
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

    private List<CategoryParam> createCategoryParams(int count) {
        List<CategoryParam> categoryParams = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            categoryParams.add(
                    new CategoryParam("high" + i,
                            new ArrayList<>(List.of("low" + i + "-1", "low" + i + "-2", "low" + i + "-3"))
                    )
            );
        }
        return categoryParams;
    }

    private List<CategoryParam> createCategoryParamsLowCategoryFlooded(int count) {
        List<CategoryParam> params = createCategoryParams(count);

        for (CategoryParam param : params) {
            param.getLowCategoryNames().add("flooded");
        }

        return params;
    }

    void setId(Designer designer, Long id) throws NoSuchFieldException, IllegalAccessException {
        Class<? extends Designer> designerClass = designer.getClass();
        Field idField = designerClass.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(designer, id);
    }
}