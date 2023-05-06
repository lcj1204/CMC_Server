package com.sctk.cmc.web.controller;

import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.abstractions.DesignerService;
import com.sctk.cmc.service.dto.designer.DesignerInfo;
import com.sctk.cmc.web.dto.DesignerInfoResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/designer")
@RequiredArgsConstructor
@Tag(name = "Designer", description = "디자이너 API Document")
public class DesignerController {
    private final DesignerService designerService;

    // 디자이너 간단 정보 조회
    @GetMapping("/{designerId}/info")
    public BaseResponse<DesignerInfoResponse> getDesignerInfo(@PathVariable("designerId") Long designerId) {
        DesignerInfo designerInfo = designerService.retrieveInfoById(designerId);

        return new BaseResponse<>(
                new DesignerInfoResponse(
                        designerInfo.getName(),
                        designerInfo.getProfileImgUrl(),
                        designerInfo.getIntroduce(),
                        designerInfo.getLikes(),
                        designerInfo.getHighCategoryNames(),
                        designerInfo.getLowCategoryNames()
                )
        );
    }
}
