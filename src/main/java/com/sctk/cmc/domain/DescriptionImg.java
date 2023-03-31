package com.sctk.cmc.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class DescriptionImg {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "description_img_id")
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
