package com.sctk.cmc.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class PortfolioImg extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_img_id")
    private Long id;
    private String url;
}
