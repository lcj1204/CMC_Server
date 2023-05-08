package com.sctk.cmc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class LowCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "low_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private Designer designer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "highCategory")
    private HighCategory highCategory;
    private String name;

    public LowCategory(Designer designer, String name) {
        this.designer = designer;
        this.name = name;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }
}
