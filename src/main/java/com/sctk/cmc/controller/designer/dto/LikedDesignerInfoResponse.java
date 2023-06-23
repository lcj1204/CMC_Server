package com.sctk.cmc.controller.designer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikedDesignerInfoResponse {
    private Long designerId;
    private String name;
    private String profileImgUrl;
    private String introduce;
    private int likes;
    private boolean liked;
}
