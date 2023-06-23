package com.sctk.cmc.service.member.product;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.domain.DescriptionImg;
import com.sctk.cmc.domain.Product;
import com.sctk.cmc.repository.member.product.MemberProductRepository;
import com.sctk.cmc.service.member.like.product.LikeProductService;
import com.sctk.cmc.service.member.product.dto.MemberProductGetDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.sctk.cmc.common.exception.ResponseStatus.PRODUCT_ILLEGAL_ID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberProductServiceImpl implements MemberProductService {
    private final MemberProductRepository memberProductRepository;
    @Override
    public Product retrieveById(Long productId) {
        return memberProductRepository.findByDesignerIdAndId(productId)
                .orElseThrow(() -> new CMCException(PRODUCT_ILLEGAL_ID));
    }

    private final LikeProductService likeProductService;

    @Override
    public MemberProductGetDetailResponse retrieveDetailById(Long memberId, Long productId) {
        Product product = retrieveById(productId);

        List<String> descriptionImgList = convertToUrlList(product);

        boolean liked = likeProductService.checkLikeProduct(memberId, productId);

        return MemberProductGetDetailResponse.of(product, product.getDesigner(), descriptionImgList, liked);
    }


    private static List<String> convertToUrlList(Product product) {
        return product.getImgs().stream()
                .map(DescriptionImg::getUrl)
                .collect(Collectors.toList());
    }
}
