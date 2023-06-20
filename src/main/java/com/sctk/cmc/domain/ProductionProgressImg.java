package com.sctk.cmc.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductionProgressImg extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "production_progress_img_id")
    private Long id;
    private String url;
    @Enumerated(EnumType.STRING)
    private ProgressType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_progress_id")
    private ProductionProgress productionProgress;

    @Builder
    public ProductionProgressImg(String url, ProgressType type, ProductionProgress productionProgress) {
        this.url = url;
        this.type = type;
        this.productionProgress = productionProgress;
        if (productionProgress != null) {
            productionProgress.getImgs().add(this);
        }
    }

    public static ProductionProgressImg create(String url, ProgressType type, ProductionProgress productionProgress) {
        return ProductionProgressImg.builder()
                .url(url)
                .type(type)
                .productionProgress(productionProgress)
                .build();
    }
}
