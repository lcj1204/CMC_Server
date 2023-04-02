package com.sctk.cmc.repository;

import com.sctk.cmc.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Repository
public class MemberRepository {
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }
}
