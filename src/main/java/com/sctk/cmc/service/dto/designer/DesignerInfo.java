package com.sctk.cmc.service.dto.designer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DesignerInfo {
    private String name;
    private String profileImgUrl;
    private String introduce;
    private int likes;
    private List<String> highCategoryNames;
    private List<String> lowCategoryNames;
}
