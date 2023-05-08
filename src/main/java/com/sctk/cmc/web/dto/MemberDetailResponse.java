package com.sctk.cmc.web.dto;

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


