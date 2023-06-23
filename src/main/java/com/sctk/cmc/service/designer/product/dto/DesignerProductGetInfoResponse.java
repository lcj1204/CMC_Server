package com.sctk.cmc.service.designer.product.dto;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Product;
import com.sctk.cmc.service.designer.dto.DesignerInfoCard;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class DesignerProductGetInfoResponse {
    private Long productId;
    private String name;
    private List<String> descriptionImgList;
    private Long designerId;
    private String designerName;
    private String designerProfileImgUrl;

    public static DesignerProductGetInfoResponse of(Product product, Designer designer, List<String> descriptionImgList) {

        return DesignerProductGetInfoResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .descriptionImgList(descriptionImgList)
                .designerId(designer.getId())
                .designerName(designer.getName())
                .designerProfileImgUrl(designer.getProfileImgUrl())
                .build();
    }
}
