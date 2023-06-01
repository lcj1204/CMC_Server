package com.sctk.cmc.controller.designer.custom.dto;

import com.sctk.cmc.domain.Custom;
import com.sctk.cmc.domain.CustomStatus;
import com.sctk.cmc.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomGetDetailResponse {
    private String title;
    private Long memberId;
    private String memberName;
    private String memberProfileImgUrl;
    private String highCategory;
    private String lowCategory;
    private Integer desiredPrice;
    private String requirement;
    private CustomStatus accepted;

    public static CustomGetDetailResponse of(Custom custom) {
        Member member = custom.getMember();

        return CustomGetDetailResponse.builder()
                .title(custom.getTitle())
                .memberId(member.getId())
                .memberName(member.getName())
                .memberProfileImgUrl(member.getProfileImgUrl())
                .highCategory(custom.getHighCategory())
                .lowCategory(custom.getLowCategory())
                .desiredPrice(custom.getDesiredPrice())
                .requirement(custom.getRequirement())
                .accepted(custom.getAccepted())
                .build();
    }
}
