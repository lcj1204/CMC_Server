package com.sctk.cmc.service.member;


import com.sctk.cmc.domain.LikeDesigner;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.controller.common.ProfileImgPostResponse;
import com.sctk.cmc.controller.member.dto.LikeDesignerResponse;
import com.sctk.cmc.service.member.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    Long join(MemberJoinParam param);

    MemberDetail retrieveDetailById(Long memberId);

    MemberInfo retrieveInfoById(Long memberId);

    Member retrieveByEmail(String email);

    boolean existsByEmail(String email);

    BodyInfoView retrieveBodyInfoById(Long memberId);
    void registerBodyInfo(Long memberId, BodyInfoParams bodyInfoParams);

    void modifyBodyInfo(Long memberId, BodyInfoModifyParams bodyInfoModifyParams);

    LikeDesignerResponse like(Long memberId, Long designerId);

    LikeDesignerResponse cancelLike(LikeDesigner like);

    ProfileImgPostResponse registerProfileImg(Long memberId, MultipartFile profileImg);

    void checkRequirements(Long memberId);
}
