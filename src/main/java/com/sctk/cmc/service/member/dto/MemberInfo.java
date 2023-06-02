package com.sctk.cmc.service.member.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfo {
    private String name;
    private String profileImgUrl;
    private BodyInfoView bodyInfoView;
}
