package com.sctk.cmc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDetailsResponse {
    private String name;
    private String nickname;
    private String email;
    private String profileImgUrl;
    private String introduce;
}
