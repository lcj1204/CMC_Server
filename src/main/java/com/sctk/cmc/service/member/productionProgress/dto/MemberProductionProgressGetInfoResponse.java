package com.sctk.cmc.service.member.productionProgress.dto;

import com.sctk.cmc.domain.ProductionProgress;
import com.sctk.cmc.domain.ProgressType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberProductionProgressGetInfoResponse {
    private Long productionProgressId;
    private String mainImg;
    private Long designerId;
    private String designerName;
    private String designerProfileImgUrl;
    private String title;
    private String category;
    private Integer price;
    private ProgressType status;

    public static MemberProductionProgressGetInfoResponse of(ProductionProgress productionProgress) {
        return MemberProductionProgressGetInfoResponse.builder()
                .productionProgressId(productionProgress.getId())
                .mainImg(productionProgress.getMainImg())
                .title(productionProgress.getTitle())
                .category(productionProgress.getCategory())
                .price(productionProgress.getPrice())
                .designerId(productionProgress.getDesigner().getId())
                .designerName(productionProgress.getDesigner().getName())
                .designerProfileImgUrl(productionProgress.getDesigner().getProfileImgUrl())
                .status(productionProgress.getStatus())
                .build();
    }

}
