package com.sctk.cmc.controller.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MemberDetailResponse {
    private String name;
    private String nickname;
    private String email;
    private String profileImgUrl;
    private String introduce;
}


