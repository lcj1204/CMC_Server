package com.sctk.cmc.service.member.product.dto;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Product;
import com.sctk.cmc.service.designer.dto.DesignerInfoCard;
import com.sctk.cmc.service.designer.product.dto.DesignerProductGetDetailResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class MemberProductGetDetailResponse extends DesignerProductGetDetailResponse {
    private Boolean liked;

    public static MemberProductGetDetailResponse of(Product product, Designer designer,
                                                    List<String> descriptionImgList, Boolean liked){
        DesignerInfoCard designerInfoCard = DesignerInfoCard.of(designer);

        return MemberProductGetDetailResponse.builder()
                .designerInfoCard(designerInfoCard)
                .productId(product.getId())
                .name(product.getName())
                .tag(product.getTag())
                .price(product.getPrice())
                .description(product.getDescription())
                .descriptionImgList(descriptionImgList)
                .productLikeCount(product.getLikeCount())
                .liked(liked)
                .build();
    }


}
