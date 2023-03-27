package com.sctk.cmc.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Size extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    private Long id;
    private String size;
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
