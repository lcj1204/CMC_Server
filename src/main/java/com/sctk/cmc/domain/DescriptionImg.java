package com.sctk.cmc.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class DescriptionImg {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "description_img_id")
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public DescriptionImg(String url, Product product) {
        this.url = url;
        this.product = product;
        if (product != null) {
            product.getDescriptionImgList().add(this);
        }
    }

    public static DescriptionImg create(String url, Product product) {
        return DescriptionImg.builder()
                .url(url)
                .product(product)
                .build();
    }
}
