package com.sctk.cmc.service.designer.dto;

import com.sctk.cmc.domain.Designer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DesignerInfoCard {
    private Long designerId;
    private String name;
    private String profileImgUrl;
    private String introduce;
    private int likes;

    public static DesignerInfoCard of(Designer designer) {
        return DesignerInfoCard.builder()
                .designerId(designer.getId())
                .name(designer.getName())
                .profileImgUrl(designer.getProfileImgUrl())
                .introduce(designer.getIntroduce())
                .likes(designer.getLikeCount())
                .build();
    }
}
