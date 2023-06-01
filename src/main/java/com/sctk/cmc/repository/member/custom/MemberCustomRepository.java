package com.sctk.cmc.repository.member.custom;

import com.sctk.cmc.domain.Custom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberCustomRepository extends JpaRepository<Custom, Long> {

    @Query("select c from Custom c " +
            "join fetch c.member " +
            "join c.designer " +
            "where c.id= :customId " +
            "and c.active= true ")
    Optional<Custom> findWithMemberById(@Param("customId") Long customId);
}
