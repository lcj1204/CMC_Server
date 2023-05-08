package com.sctk.cmc.web.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DesignerInfoResponse {
    private String name;
    private String profileImgUrl;
    private String introduce;
    private int likes;
    private List<String> highCategoryNames;
    private List<String> lowCategoryNames;
}
