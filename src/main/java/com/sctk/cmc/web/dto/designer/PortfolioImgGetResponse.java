package com.sctk.cmc.web.dto.designer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PortfolioImgGetResponse {
    private List<String> portfolioImgUrls;
}
