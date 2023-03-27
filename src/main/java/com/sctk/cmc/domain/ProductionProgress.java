package com.sctk.cmc.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class ProductionProgress extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "production_progress_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private ProgressType status;

    /**
     * 양방향 매핑
     */
    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private Product product;

    @OneToMany(mappedBy = "productionProgress")
    private List<ProductionProgressImg> imgs = new ArrayList<>();
}
