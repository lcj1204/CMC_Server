package com.sctk.cmc.service.dto.designer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PopularDesignerInfo implements FilteredDesignerInfo {
    private String name;
    private String profileImgUrl;
    private List<String> categoryNames = new ArrayList<>(3);
}
