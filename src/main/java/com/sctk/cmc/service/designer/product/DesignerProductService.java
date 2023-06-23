package com.sctk.cmc.service.designer.product;

import com.sctk.cmc.service.designer.product.dto.DesignerProductIdResponse;
import com.sctk.cmc.service.designer.product.dto.DesignerProductRegisterParams;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DesignerProductService {
    DesignerProductIdResponse register(Long designerId,
                                       DesignerProductRegisterParams designerProductRegisterParams,
                                       List<MultipartFile> ThumbnailImgs,
                                       List<MultipartFile> DescriptionImgs);
}
