package com.sctk.cmc.service;

import com.sctk.cmc.common.dto.designer.CategoryView;
import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.HighCategory;
import com.sctk.cmc.domain.LowCategory;
import com.sctk.cmc.common.dto.designer.CategoryParam;
import com.sctk.cmc.service.dto.designer.DesignerInfo;
import com.sctk.cmc.common.dto.designer.DesignerJoinParam;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.repository.DesignerRepository;
import com.sctk.cmc.service.abstractions.DesignerService;
import com.sctk.cmc.service.dto.designer.FilteredDesignerInfo;
import com.sctk.cmc.service.dto.designer.FreshDesignerInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sctk.cmc.common.exception.ResponseStatus.AUTHENTICATION_ILLEGAL_EMAIL;
import static com.sctk.cmc.common.exception.ResponseStatus.DESIGNERS_ILLEGAL_ID;

@RequiredArgsConstructor
@Service
public class DesignerServiceImpl implements DesignerService {
    private final PasswordEncoder passwordEncoder;
    private final DesignerRepository designerRepository;

    @Transactional
    @Override
    public Long join(DesignerJoinParam param) {
        return designerRepository.save(Designer.builder()
                .name(param.getName())
                .nickname(param.getNickname())
                .email(param.getEmail())
                .password(passwordEncoder.encode(param.getPassword()))
                .contact(param.getContact())
                .build());
    }

    @Override
    public DesignerInfo retrieveInfoById(Long designerId) {
        Designer designer = retrieveById(designerId);

        List<String> highCategoryNames = designer.getHighCategories().stream()
                .map(category -> category.getName())
                .collect(Collectors.toUnmodifiableList());

        List<String> lowCategoryNames = designer.getLowCategories().stream()
                .map(category -> category.getName())
                .collect(Collectors.toUnmodifiableList());

        return new DesignerInfo(
                designer.getName(),
                designer.getProfileImgUrl(),
                designer.getIntroduce(),
                designer.getLikeCount(),
                highCategoryNames,
                lowCategoryNames
        );
    }


    public Designer retrieveById(Long designerId) {
        return designerRepository.findById(designerId)
                .orElseThrow(() -> new CMCException(DESIGNERS_ILLEGAL_ID));
    }

    @Override
    public Designer retrieveByEmail(String email) {
        return designerRepository.findByEmail(email)
                .orElseThrow(() -> new CMCException(AUTHENTICATION_ILLEGAL_EMAIL));
    }

    @Override
    public boolean existsByEmail(String email) {
        return designerRepository.findByEmail(email)
                .isPresent();
    }

    @Override
    public List<Designer> retrieveAllByName(String name) {
        return designerRepository.findAllByName(name);
    }

    @Transactional
    @Override
    public int registerCategories(Long designerId, List<CategoryParam> categoryParams) {
        Designer designer = retrieveById(designerId);

        for (CategoryParam params : categoryParams) {
            HighCategory highCategory = new HighCategory(designer, params.getHighCategoryName());

            for (String lowCategoryName : params.getLowCategoryNames()) {
                LowCategory lowCategory = new LowCategory(designer, highCategory, lowCategoryName);
            }
        }

        return designer.getHighCategories().size();
    }

    @Override
    public List<CategoryView> retrieveAllCategoryViewById(Long designerId) {
        Designer designer = retrieveById(designerId);

        List<CategoryView> categoryViews = new ArrayList<>();

        designer.getHighCategories()
                .stream()
                .forEach(highCategory -> categoryViews.add(
                                new CategoryView(highCategory.getName(), highCategory.getLowCategoryNames())
                        )
                );
        return categoryViews;
    }

    @Override
    public List<FilteredDesignerInfo> retrievePopularsByCriteria(String criteria, int limit) {
        return null;
    }

    @Override
    public List<FilteredDesignerInfo> retrieveSortedBy(String criteria, int limit) {
        return null;
    }

    @Override
    public List<FilteredDesignerInfo> retrieveAllFreshFrom(LocalDate targetDate, int limit) {
        List<Designer> designers = designerRepository.findLastSavedAfter(targetDate, limit);

        return designers.stream()
                .map(designer -> new FreshDesignerInfo(
                        designer.getName(),
                        designer.getProfileImgUrl(),
                        designer.getHighCategoryNames()))
                .collect(Collectors.toUnmodifiableList());
    }
}
