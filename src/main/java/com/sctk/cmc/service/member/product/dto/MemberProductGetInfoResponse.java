package com.sctk.cmc.service.member.product.dto;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Product;
import com.sctk.cmc.service.designer.dto.DesignerInfoCard;
import com.sctk.cmc.service.designer.product.dto.DesignerProductGetDetailResponse;
import com.sctk.cmc.service.designer.product.dto.DesignerProductGetInfoResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class MemberProductGetInfoResponse extends DesignerProductGetInfoResponse {
    private Boolean liked;

    public static MemberProductGetInfoResponse of(Product product, Designer designer,
                                                  List<String> descriptionImgList, Boolean liked){

        return MemberProductGetInfoResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .descriptionImgList(descriptionImgList)
                .designerId(designer.getId())
                .designerName(designer.getName())
                .designerProfileImgUrl(designer.getProfileImgUrl())
                .liked(liked)
                .build();
    }


}
