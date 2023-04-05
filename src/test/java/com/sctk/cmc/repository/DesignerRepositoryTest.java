package com.sctk.cmc.repository;

import com.sctk.cmc.domain.Designer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class DesignerRepositoryTest {
    @Autowired
    DesignerRepository designerRepository;

    @Test
    public void designer_저장_테스트() throws Exception {
        Designer designer = createDesigner();

        Long returned = designerRepository.save(designer);

        assertThat(designer.getId()).isEqualTo(returned);
    }

    @Test
    public void designer_name으로_조회() throws Exception {
        //given
        String name = "designer";
        String email = "email";
        Designer designer = createDesignerWithNameEmail(name, email);
        designerRepository.save(designer);

        //when
        Designer findDesigner = designerRepository.findByName(name)
                .orElse(null);

        //then
        assertThat(findDesigner).isNotNull();
        assertThat(findDesigner.getId().equals(designer.getId())).isTrue();
        assertThat(findDesigner.getEmail().equals(designer.getEmail())).isTrue();
    }

    @Test
    public void designer_email로_조회() throws Exception {
        //given
        String name = "designer";
        String email = "email";
        Designer designer = createDesignerWithNameEmail(name, email);
        designerRepository.save(designer);

        //when
        Designer findDesigner = designerRepository.findByEmail(email)
                .orElse(null);

        //then
        assertThat(findDesigner).isNotNull();
        assertThat(findDesigner.getId().equals(designer.getId())).isTrue();
        assertThat(findDesigner.getEmail().equals(designer.getEmail())).isTrue();
    }

    public Designer createDesigner() {
        return Designer.builder()
                .build();
    }

    public Designer createDesignerWithNameEmail(String name, String email) {
        return Designer.builder()
                .name(name)
                .email(email)
                .build();
    }
}