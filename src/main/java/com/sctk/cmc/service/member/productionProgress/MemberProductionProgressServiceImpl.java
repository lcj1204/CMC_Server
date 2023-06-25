package com.sctk.cmc.service.member.productionProgress;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.domain.ProductionProgress;
import com.sctk.cmc.domain.ProductionProgressImg;
import com.sctk.cmc.domain.ProgressType;
import com.sctk.cmc.repository.member.productionProgress.MemberProductionProgressRepository;
import com.sctk.cmc.service.member.productionProgress.dto.MemberProductionProgressGetDetailResponse;
import com.sctk.cmc.service.member.productionProgress.dto.MemberProductionProgressGetInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sctk.cmc.common.exception.ResponseStatus.PRODUCTION_PROGRESS_ILLEGAL_ID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberProductionProgressServiceImpl implements MemberProductionProgressService {
    private final MemberProductionProgressRepository memberProductionProgressRepository;

    @Override
    public List<MemberProductionProgressGetInfoResponse> retrieveAllInfo(Long memberId) {
        List<ProductionProgress> allProductionProgress = retrieveAllProductionProgress(memberId);

        return allProductionProgress.stream()
                .map(MemberProductionProgressGetInfoResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public MemberProductionProgressGetDetailResponse retrieveDetailById(Long memberId, Long productionProgressId) {

        ProductionProgress productionProgress = retrieveProductionProgress(memberId, productionProgressId);

        MemberProductionProgressGetInfoResponse productionProgressInfo
                = MemberProductionProgressGetInfoResponse.of(productionProgress);

        Map<ProgressType, List<String>> progressTypeListMap = retrieveProductionProgressAllImg(productionProgress);

        Long period = ChronoUnit.DAYS.between(LocalDate.now(), productionProgress.getExpectEndDate());

        return MemberProductionProgressGetDetailResponse.of(period, productionProgressInfo, progressTypeListMap);
    }

    private List<ProductionProgress> retrieveAllProductionProgress(Long memberId) {
        return memberProductionProgressRepository.findAllByMemberId(memberId);
    }

    private ProductionProgress retrieveProductionProgress(Long memberId, Long productionProgressId) {
        return memberProductionProgressRepository.findWithImgByMemberIdAndId(memberId, productionProgressId)
        .orElseThrow(() -> new CMCException(PRODUCTION_PROGRESS_ILLEGAL_ID));
    }

    private static Map<ProgressType, List<String>> retrieveProductionProgressAllImg(ProductionProgress productionProgress) {
        return productionProgress.getImgs().stream()
                .filter(i -> !i.getType().equals(ProgressType.ACCEPT))
                .collect(Collectors.groupingBy(ProductionProgressImg::getType,
                        Collectors.mapping(ProductionProgressImg::getUrl, Collectors.toList())));
    }
}
