package com.sctk.cmc.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;
    private int price;
    private String tag;
    private String description;
    private LocalTime releaseAt;
    private int likeCount;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    /**
     * 단방향 매핑
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_progress_id")
    private ProductionProgress productionProgress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_id")
    private Custom custom;

    /**
     * 양방향 매핑
     */
    @OneToMany(mappedBy = "product")
    private List<Size> sizes = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    private List<LikeProduct> likeProducts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<DescriptionImg> imgs = new ArrayList<>();
}
