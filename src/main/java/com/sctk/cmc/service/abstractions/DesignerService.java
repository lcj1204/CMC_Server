package com.sctk.cmc.service.abstractions;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.common.dto.designer.CategoryParam;
import com.sctk.cmc.common.dto.designer.CategoryView;
import com.sctk.cmc.service.dto.designer.FilteredDesignerInfo;
import com.sctk.cmc.service.dto.designer.DesignerInfo;
import com.sctk.cmc.common.dto.designer.DesignerJoinParam;

import java.time.LocalDate;
import java.util.List;

public interface DesignerService {
    Long join(DesignerJoinParam param);

    DesignerInfo retrieveInfoById(Long designerId);

    Designer retrieveByEmail(String email);

    boolean existsByEmail(String email);

    List<Designer> retrieveAllByName(String name);

    int registerCategories(Long designerId, List<CategoryParam> highCategories);

    List<CategoryView> retrieveAllCategoryViewById(Long designerId);

    List<FilteredDesignerInfo> retrievePopularsByCriteria(String criteria, int limit);

    List<FilteredDesignerInfo> retrieveSortedBy(String criteria, int limit);

    List<FilteredDesignerInfo> retrieveAllFreshFrom(LocalDate targetDate, int limit);
}
