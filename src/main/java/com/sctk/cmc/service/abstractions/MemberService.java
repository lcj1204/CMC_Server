package com.sctk.cmc.service.abstractions;


import com.sctk.cmc.domain.Member;
import com.sctk.cmc.dto.member.MemberJoinParam;

public interface MemberService {
    Long join(MemberJoinParam param);

    Member retrieveById(Long id);

    Member retrieveByEmail(String email);

    boolean existsByEmail(String email);
}
