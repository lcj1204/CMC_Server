package com.sctk.cmc.service.abstractions;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.common.dto.designer.CategoryParam;
import com.sctk.cmc.common.dto.designer.CategoryView;
import com.sctk.cmc.service.dto.designer.FilteredDesignerInfo;
import com.sctk.cmc.service.dto.designer.DesignerInfo;
import com.sctk.cmc.common.dto.designer.DesignerJoinParam;
import com.sctk.cmc.web.dto.ProfileImgPostResponse;
import com.sctk.cmc.web.dto.designer.PortfolioImgGetResponse;
import com.sctk.cmc.web.dto.designer.PortfolioImgPostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface DesignerService {
    Long join(DesignerJoinParam param);

    Designer retrieveById(Long designerId);

    Designer retrieveByEmail(String email);

    DesignerInfo retrieveInfoById(Long designerId);

    boolean existsByEmail(String email);

    List<Designer> retrieveAllByName(String name);

    int registerCategories(Long designerId, List<CategoryParam> highCategories);

    List<CategoryView> retrieveAllCategoryViewById(Long designerId);


    List<FilteredDesignerInfo> retrieveSortedBy(String criteria, int limit);

    List<FilteredDesignerInfo> retrieveAllFreshFrom(LocalDate targetDate, int limit);

    List<FilteredDesignerInfo> retrievePopularByLike(int limit);

    List<FilteredDesignerInfo> retrievePopularByCategory(int limit);

    ProfileImgPostResponse registerProfileImg(Long designerId, MultipartFile profileImg);

    PortfolioImgPostResponse registerPortfolioImg(Long designerId, MultipartFile portfolioImg);

    PortfolioImgGetResponse retrieveAllPortfolioImgById(Long designerId);
}
