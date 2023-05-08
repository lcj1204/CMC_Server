package com.sctk.cmc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class HighCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "high_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private Designer designer;

    @OneToMany(mappedBy = "highCategory")
    private List<LowCategory> lowCategories = new ArrayList<>(3);

    private String name;

    public HighCategory(Designer designer, String name) {
        this.designer = designer;
        this.name = name;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }
}
