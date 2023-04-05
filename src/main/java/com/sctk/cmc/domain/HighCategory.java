package com.sctk.cmc.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    private String name;

    @OneToMany(mappedBy = "highCategory")
    private List<LowCategory> lowCategorys;

    public HighCategory(Designer designer, String name) {
        this.designer = designer;
        this.name = name;
    }
}
