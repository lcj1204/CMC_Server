package com.sctk.cmc.service.abstractions;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.common.dto.designer.CategoryParams;
import com.sctk.cmc.service.dto.designer.DesignerInfo;
import com.sctk.cmc.common.dto.designer.DesignerJoinParam;

import java.util.List;

public interface DesignerService {
    Long join(DesignerJoinParam param);

    DesignerInfo retrieveInfoById(Long designerId);

    Designer retrieveByEmail(String email);

    boolean existsByEmail(String email);

    List<Designer> retrieveAllByName(String name);

    int registerCategories(Long designerId, List<CategoryParams> highCategories);
}
