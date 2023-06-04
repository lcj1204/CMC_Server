package com.sctk.cmc.controller.member.custom.dto;

import com.sctk.cmc.domain.Custom;
import com.sctk.cmc.domain.CustomStatus;
import com.sctk.cmc.domain.Designer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberCustomGetInfoResponse {
    private Long customId;
    private String title;
    private String customThumbnailImgUrl;
    private Long designerId;
    private String designerName;
    private String designerProfileImgUrl;
    private CustomStatus accepted;

    public static MemberCustomGetInfoResponse of(Custom custom, String customThumbnailImgUrl) {
        Designer designer = custom.getDesigner();

        return MemberCustomGetInfoResponse.builder()
                .customId( custom.getId() )
                .title( custom.getTitle() )
                .customThumbnailImgUrl( customThumbnailImgUrl )
                .designerId( designer.getId() )
                .designerName( designer.getName() )
                .designerProfileImgUrl( designer.getProfileImgUrl() )
                .accepted( custom.getAccepted() )
                .build();
    }
}
