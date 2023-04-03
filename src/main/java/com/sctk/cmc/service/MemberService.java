package com.sctk.cmc.service;


import com.sctk.cmc.domain.Member;
import com.sctk.cmc.dto.member.MemberJoinParam;

public interface MemberService {
    Long join(MemberJoinParam param);

    Member retrieveByEmail(String email);
}
