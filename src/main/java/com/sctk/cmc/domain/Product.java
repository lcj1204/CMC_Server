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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private Designer designer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_id")
    private Custom custom;

    @OneToMany(mappedBy = "product")
    private List<Size> sizes = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    private List<LikeProduct> likeProducts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<DescriptionImg> imgs = new ArrayList<>();
}
