package com.sctk.cmc.domain;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Portfolio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    private Designer designer;
    private List<PortfolioImg> portfolioImgs = new ArrayList<>();
}
