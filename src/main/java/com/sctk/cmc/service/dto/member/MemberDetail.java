package com.sctk.cmc.service.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDetail {
    private String name;
    private String nickname;
    private String email;
    private String profileImgUrl;
    private String introduce;
}
