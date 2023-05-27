package com.sctk.cmc.service;

import com.sctk.cmc.common.dto.designer.CategoryView;
import com.sctk.cmc.domain.*;
import com.sctk.cmc.common.dto.designer.CategoryParam;
import com.sctk.cmc.service.dto.designer.DesignerInfo;
import com.sctk.cmc.common.dto.designer.DesignerJoinParam;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.repository.DesignerRepository;
import com.sctk.cmc.service.abstractions.DesignerService;
import com.sctk.cmc.service.dto.designer.FilteredDesignerInfo;
import com.sctk.cmc.service.dto.designer.FreshDesignerInfo;
import com.sctk.cmc.service.dto.designer.PopularDesignerInfo;
import com.sctk.cmc.web.dto.ProfileImgPostResponse;
import com.sctk.cmc.web.dto.designer.PortfolioImgGetResponse;
import com.sctk.cmc.web.dto.designer.PortfolioImgPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sctk.cmc.common.exception.ResponseStatus.*;

@RequiredArgsConstructor
@Service
public class DesignerServiceImpl implements DesignerService {
    private final PasswordEncoder passwordEncoder;
    private final DesignerRepository designerRepository;
    private final AmazonS3Service amazonS3Service;
    @Value("${cmc.designer.profile.default-img-url}")
    private String DESIGNER_DEFAULT_PROFILE_IMG_URL;

    @Transactional
    @Override
    public Long join(DesignerJoinParam param) {
        if (existsByEmail(param.getEmail())) {
            throw new CMCException(AUTHENTICATION_DUPLICATE_EMAIL);
        }

        return designerRepository.save(Designer.builder()
                .name(param.getName())
                .nickname(param.getNickname())
                .email(param.getEmail())
                .password(passwordEncoder.encode(param.getPassword()))
                .profileImgUrl(DESIGNER_DEFAULT_PROFILE_IMG_URL)
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

    @Override
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

    @Override
    public List<FilteredDesignerInfo> retrievePopularByLike(int limit) {
        List<FilteredDesignerInfo> popularDesigners = new ArrayList<>();

        List<Designer> orderedDesigners = designerRepository.findAllOrderByLikeCount(limit);

        orderedDesigners.stream()
                .map(designer ->
                        new PopularDesignerInfo(
                                designer.getName(),
                                designer.getProfileImgUrl(),
                                designer.getHighCategoryNames(),
                                designer.getLikeCount())
                ).forEach(info -> popularDesigners.add(info));

        return popularDesigners;
    }

    @Override
    public List<FilteredDesignerInfo> retrievePopularByCategory(int limit) {
        return null;
    }

    @Transactional
    @Override
    public ProfileImgPostResponse registerProfileImg(Long designerId, MultipartFile profileImg) {
        Designer designer = retrieveById(designerId);

        if (!designer.getProfileImgUrl().equals(DESIGNER_DEFAULT_PROFILE_IMG_URL)) {
            amazonS3Service.delete(designer.getProfileImgUrl());
        }

        String uploadedUrl = amazonS3Service.uploadProfileImg(profileImg, designerId, designer.getRole());
        designer.setProfileImgUrl(uploadedUrl);
        return new ProfileImgPostResponse(uploadedUrl);
    }

    @Transactional
    @Override
    public PortfolioImgPostResponse registerPortfolioImg(Long designerId, MultipartFile portfolioImg) {
        Designer designer = retrieveById(designerId);
        Portfolio portfolio = designer.getPortfolio();

        if (portfolio == null) {
            portfolio = new Portfolio(designer);
        }

        String imgUrl = amazonS3Service.uploadPortfolioImg(portfolioImg, designerId);

        PortfolioImg img = new PortfolioImg(portfolio, imgUrl, portfolio.getPortfolioImgs().size());

        return new PortfolioImgPostResponse(img.getUrl(), img.getOrderInRow());
    }

    @Override
    public PortfolioImgGetResponse retrieveAllPortfolioImgById(Long designerId) {
        Designer designer = retrieveById(designerId);

        List<String> imgUrls = designer.getPortfolio().getPortfolioImgUrls();

        return new PortfolioImgGetResponse(imgUrls);
    }
}
