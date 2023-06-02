package com.sctk.cmc.controller.member.dto;

import com.sctk.cmc.service.member.dto.BodyInfoView;
import com.sctk.cmc.service.member.dto.MemberInfo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MemberInfoResponse {
    private MemberInfo memberInfo;
}


