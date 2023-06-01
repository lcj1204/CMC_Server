package com.sctk.cmc.service.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfo {
    private String name;
    private String profileImgUrl;
    private BodyInfoView bodyInfoView;
}
