package com.sctk.cmc.controller.designer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PortfolioImgGetResponse {
    private List<String> portfolioImgUrls;
}
