package com.sctk.cmc.service.common.product.dto;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Product;
import com.sctk.cmc.service.designer.dto.DesignerInfoCard;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ProductGetDetailResponse {
    private DesignerInfoCard designerInfoCard;
    private Long productId;
    private String name;
    private String tag;
    private int price;
    private String description;
    private List<String> thumbnailImgUrlList;
    private List<String> descriptionImgList;
    private int productLikeCount;

    public static ProductGetDetailResponse of(Product product, Designer designer,
                                              List<String> thumbnailImgUrlList , List<String> descriptionImgList) {

        DesignerInfoCard designerInfoCard = DesignerInfoCard.of(designer);

        return ProductGetDetailResponse.builder()
                .designerInfoCard(designerInfoCard)
                .productId(product.getId())
                .name(product.getName())
                .tag(product.getTag())
                .price(product.getPrice())
                .description(product.getDescription())
                .thumbnailImgUrlList(thumbnailImgUrlList)
                .descriptionImgList(descriptionImgList)
                .productLikeCount(product.getLikeCount())
                .build();
    }
}
