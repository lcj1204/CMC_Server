package com.sctk.cmc.service.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberJoinParam {
    private String name;
    private String nickname;
    private String email;
    private String password;
}
