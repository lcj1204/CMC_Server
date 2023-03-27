package com.sctk.cmc.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class ProductionProgressImg extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "production_progress_img_id")
    private Long id;
    private String url;
    @Enumerated(EnumType.STRING)
    private ProgressType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "production_progress_id")
    private ProductionProgress productionProgress;
}
