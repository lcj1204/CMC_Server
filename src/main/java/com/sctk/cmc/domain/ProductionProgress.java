package com.sctk.cmc.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class ProductionProgress extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "production_progress_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_id")
    private Custom custom;

    @Enumerated(EnumType.STRING)
    private ProgressType status;

    private String mainImg;

    private String title;

    private int price;

    private LocalDate expectStartDate;

    private LocalDate expectEndDate;

    @OneToMany(mappedBy = "productionProgress")
    private List<ProductionProgressImg> imgs = new ArrayList<>();
}
