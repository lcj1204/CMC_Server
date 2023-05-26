package com.sctk.cmc.service.abstractions;


import com.sctk.cmc.common.dto.member.MemberJoinParam;
import com.sctk.cmc.domain.LikeDesigner;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.service.dto.member.*;
import com.sctk.cmc.web.dto.ProfileImgPostResponse;
import com.sctk.cmc.web.dto.member.LikeDesignerResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    Long join(MemberJoinParam param);

    MemberDetail retrieveDetailById(Long memberId);

    MemberInfo retrieveInfoById(Long memberId);

    Member retrieveByEmail(String email);

    boolean existsByEmail(String email);

    void registerBodyInfo(Long memberId, BodyInfoParams bodyInfoParams);

    void modifyBodyInfo(Long memberId, BodyInfoModifyParams bodyInfoModifyParams);

    LikeDesignerResponse like(Long memberId, Long designerId);

    LikeDesignerResponse cancelLike(LikeDesigner like);

    ProfileImgPostResponse registerProfileImg(Long memberId, MultipartFile profileImg);
}
