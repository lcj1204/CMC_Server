package com.sctk.cmc.service.abstractions;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.HighCategory;
import com.sctk.cmc.domain.LowCategory;
import com.sctk.cmc.service.dto.designer.DesignerJoinParam;

import java.util.List;

public interface DesignerService {
    Long join(DesignerJoinParam param);

    Designer retrieveById(Long designerId);

    Designer retrieveByEmail(String email);

    boolean existsByEmail(String email);

    List<Designer> retrieveAllByName(String name);

    int registerHighCategories(Long designerId, List<HighCategory> highCategories);

    int registerLowCategories(Long designerId, List<LowCategory> lowCategories);
}
