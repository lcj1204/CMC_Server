package com.sctk.cmc.controller.designer.custom.dto;

import com.sctk.cmc.domain.Custom;
import com.sctk.cmc.domain.CustomReferenceImg;
import com.sctk.cmc.domain.CustomStatus;
import com.sctk.cmc.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CustomGetDetailResponse {
    private Long customId;
    private String title;
    private Long memberId;
    private String memberName;
    private String memberProfileImgUrl;
    private String highCategory;
    private String lowCategory;
    private Integer desiredPrice;
    private String requirement;
    private CustomStatus accepted;
    private List<String> customReferenceImgs;

    public static CustomGetDetailResponse of(Custom custom) {
        Member member = custom.getMember();

        return CustomGetDetailResponse.builder()
                .customId(custom.getId())
                .title(custom.getTitle())
                .memberId(member.getId())
                .memberName(member.getName())
                .memberProfileImgUrl(member.getProfileImgUrl())
                .highCategory(custom.getHighCategory())
                .lowCategory(custom.getLowCategory())
                .desiredPrice(custom.getDesiredPrice())
                .requirement(custom.getRequirement())
                .accepted(custom.getAccepted())
                .customReferenceImgs(
                        custom.getReference().getReferenceImgs().stream()
                                .map(CustomReferenceImg::getUrl)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
