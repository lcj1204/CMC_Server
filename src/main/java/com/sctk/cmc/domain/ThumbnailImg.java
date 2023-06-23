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
public class ThumbnailImg {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thumbnail_img_id")
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public ThumbnailImg(String url, Product product) {
        this.url = url;
        this.product = product;
        if (product != null) {
            product.getThumbnailImgList().add(this);
        }
    }

    public static ThumbnailImg create(String url, Product product) {
        return ThumbnailImg.builder()
                .url(url)
                .product(product)
                .build();
    }
}
