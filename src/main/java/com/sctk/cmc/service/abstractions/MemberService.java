package com.sctk.cmc.service.abstractions;


import com.sctk.cmc.domain.Member;
import com.sctk.cmc.service.dto.BodyInfoParams;
import com.sctk.cmc.service.dto.member.MemberDetails;
import com.sctk.cmc.service.dto.member.MemberJoinParam;

public interface MemberService {
    Long join(MemberJoinParam param);

    MemberDetails retrieveById(Long memberId);

    Member retrieveByEmail(String email);

    boolean existsByEmail(String email);

    void registerBodyInfo(Long memberId, BodyInfoParams bodyInfoParams);
}
