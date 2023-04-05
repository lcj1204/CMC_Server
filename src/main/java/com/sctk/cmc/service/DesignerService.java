package com.sctk.cmc.service;


import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.dto.designer.DesignerJoinParam;

import java.util.List;

public interface DesignerService {
    Long join(DesignerJoinParam param);

    Designer retrieveByEmail(String email);

    boolean existsByEmail(String email);

    List<Designer> retrieveAllByName(String name);
}
