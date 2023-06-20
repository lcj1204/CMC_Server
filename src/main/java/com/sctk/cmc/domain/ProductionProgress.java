package com.sctk.cmc.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private String category;

    private int price;

    private LocalDate expectStartDate;

    private LocalDate expectEndDate;

    @OneToMany(mappedBy = "productionProgress")
    private List<ProductionProgressImg> imgs = new ArrayList<>();

    @Builder
    public ProductionProgress(Custom custom, ProgressType status, String mainImg, String title, String category, int price, LocalDate expectStartDate, LocalDate expectEndDate) {
        this.custom = custom;
        this.status = status;
        this.mainImg = mainImg;
        this.title = title;
        this.category = category;
        this.price = price;
        this.expectStartDate = expectStartDate;
        this.expectEndDate = expectEndDate;
    }

    public static ProductionProgress create(Custom custom) {
        return ProductionProgress.builder()
                .custom( custom )
                .status( ProgressType.ACCEPT )
                .mainImg( custom.getReference().getReferenceImgs().get(0).getUrl() )
                .title( custom.getTitle() )
                .category( custom.getLowCategory() )
                .price( custom.getCustomResult().getExpectPrice() )
                .expectStartDate( custom.getCustomResult().getExpectStartDate() )
                .expectEndDate( custom.getCustomResult().getExpectEndDate() )
                .build();
    }
}
