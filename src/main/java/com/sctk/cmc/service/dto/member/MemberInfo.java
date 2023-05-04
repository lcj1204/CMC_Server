package com.sctk.cmc.service.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfo {
    private String name;
    private String profileImgUrl;
    private BodyInfoView bodyInfoView;
}
