package com.sctk.cmc.service.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinParam {
    private String name;
    private String nickname;
    private String email;
    private String password;
}
