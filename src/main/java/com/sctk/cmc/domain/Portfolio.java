package com.sctk.cmc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Portfolio extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private Designer designer;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioImg> portfolioImgs;

    public Portfolio(Designer designer) {
        this.designer = designer;
        this.portfolioImgs = new ArrayList<>();
        designer.setPortfolio(this);
    }

    // Getter
    public List<String> getPortfolioImgUrls() {
        List<String> portfolioImgUrls = new ArrayList<>();

        for (PortfolioImg img : portfolioImgs) {
            portfolioImgUrls.add(img.getUrl());
        }

        return Collections.unmodifiableList(portfolioImgUrls);
    }

    // Setter
    public void addImg(PortfolioImg img) {
        this.portfolioImgs.add(img);
    }
}