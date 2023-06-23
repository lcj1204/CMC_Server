package com.sctk.cmc.controller.designer.dto;

import com.sctk.cmc.service.designer.dto.DesignerInfoCard;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DesignerGetBySearchingResponse {
    private DesignerInfoCard designerInfoCard;
}
