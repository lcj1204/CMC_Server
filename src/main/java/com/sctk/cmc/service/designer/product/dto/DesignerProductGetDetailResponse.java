package com.sctk.cmc.service.designer.product.dto;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Product;
import com.sctk.cmc.service.designer.dto.DesignerInfoCard;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class DesignerProductGetDetailResponse {
    private DesignerInfoCard designerInfoCard;
    private Long productId;
    private String name;
    private String tag;
    private int price;
    private String description;
    private List<String> descriptionImgList;
    private int productLikeCount;

    public static DesignerProductGetDetailResponse of(Product product, Designer designer, List<String> descriptionImgList) {
        DesignerInfoCard designerInfoCard = DesignerInfoCard.of(designer);

        return DesignerProductGetDetailResponse.builder()
                .designerInfoCard(designerInfoCard)
                .productId(product.getId())
                .name(product.getName())
                .tag(product.getTag())
                .price(product.getPrice())
                .description(product.getDescription())
                .descriptionImgList(descriptionImgList)
                .productLikeCount(product.getLikeCount())
                .build();
    }
}
