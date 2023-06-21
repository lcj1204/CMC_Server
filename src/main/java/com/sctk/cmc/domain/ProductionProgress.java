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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private Designer designer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private ProgressType status;

    private String mainImg;

    private String title;

    private String category;

    private int price;

    private LocalDate expectStartDate;

    private LocalDate expectEndDate;

    @OneToMany(mappedBy = "productionProgress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductionProgressImg> imgs = new ArrayList<>();

    private Boolean active;

    @Builder
    public ProductionProgress(Designer designer, Member member, ProgressType status, String mainImg, String title, String category, int price, LocalDate expectStartDate, LocalDate expectEndDate, Boolean active) {
        this.designer = designer;
        this.member = member;
        this.status = status;
        this.mainImg = mainImg;
        this.title = title;
        this.category = category;
        this.price = price;
        this.expectStartDate = expectStartDate;
        this.expectEndDate = expectEndDate;
        this.active = active;
    }

    public static ProductionProgress create(Designer designer, Member member, Custom custom) {
        return ProductionProgress.builder()
                .designer(designer)
                .member(member)
                .status(ProgressType.ACCEPT)
                .mainImg(custom.getReference().getReferenceImgs().get(0).getUrl())
                .title(custom.getTitle())
                .category(custom.getLowCategory())
                .price(custom.getCustomResult().getExpectPrice())
                .expectStartDate(custom.getCustomResult().getExpectStartDate())
                .expectEndDate(custom.getCustomResult().getExpectEndDate())
                .active(true)
                .build();
    }

    public void addProductionProgressImg(ProductionProgressImg productionProgressImg) {
        imgs.add(productionProgressImg);
        if (status.getPriority() < productionProgressImg.getType().getPriority()) {
            status = productionProgressImg.getType();
        }
    }
}
