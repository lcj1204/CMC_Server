package com.sctk.cmc.service.abstractions;


import com.sctk.cmc.domain.Member;
import com.sctk.cmc.service.dto.member.*;

public interface MemberService {
    Long join(MemberJoinParam param);

    MemberDetails retrieveDetailsById(Long memberId);

    MemberInfo retrieveInfoById(Long memberId);

    Member retrieveByEmail(String email);

    boolean existsByEmail(String email);

    void registerBodyInfo(Long memberId, BodyInfoParams bodyInfoParams);

    void modifyBodyInfo(Long memberId, BodyInfoModifyParams bodyInfoModifyParams);
}
