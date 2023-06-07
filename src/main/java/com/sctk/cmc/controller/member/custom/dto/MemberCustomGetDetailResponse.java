package com.sctk.cmc.controller.member.custom.dto;

import com.sctk.cmc.domain.Custom;
import com.sctk.cmc.domain.CustomReferenceImg;
import com.sctk.cmc.domain.CustomStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class MemberCustomGetDetailResponse {
    private Long customId;
    private String title;
    private String highCategory;
    private String lowCategory;
    private Integer desiredPrice;
    private String requirement;
    private CustomStatus accepted;
    private Long customResultId;
    private List<String> customReferenceImgs;

    public static MemberCustomGetDetailResponse of(Custom custom) {
        return MemberCustomGetDetailResponse.builder()
                .customId(custom.getId())
                .title(custom.getTitle())
                .highCategory(custom.getHighCategory())
                .lowCategory(custom.getLowCategory())
                .desiredPrice(custom.getDesiredPrice())
                .requirement(custom.getRequirement())
                .accepted(custom.getAccepted())
                .customResultId( custom.getAccepted() == CustomStatus.REQUESTING ? null : custom.getCustomResult().getId())
                .customReferenceImgs(
                        custom.getReference().getReferenceImgs().stream()
                                .map(CustomReferenceImg::getUrl)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
