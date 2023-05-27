package com.sctk.cmc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PortfolioImg extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_img_id")
    private Long id;

    private String url;
    private int orderInRow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    // Constructor
    public PortfolioImg(Portfolio portfolio, String url, int orderInRow) {
        this.portfolio = portfolio;
        portfolio.addImg(this);
        this.url = url;
        this.orderInRow = orderInRow;
    }

    // Setter
    public void changeOrderInRow(int orderInRow) {
        this.orderInRow = orderInRow;
    }
}