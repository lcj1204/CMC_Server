package com.sctk.cmc.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginParam {
    private String email;
    private String password;
}
