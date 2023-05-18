package com.sctk.cmc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    // Constructor
    public LowCategory(Designer designer, HighCategory highCategory,String name) {
        this.highCategory = highCategory;
        highCategory.addLowCategory(this);

        this.designer = designer;
        designer.addLowCategory(this);

        this.name = name;
    }
}
