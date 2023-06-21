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

    @Query("select distinct c from Custom c " +
            "join fetch c.member " +
            "join fetch c.designer " +
            "join fetch c.reference cr " +
            "join fetch cr.referenceImgs " +
            "where c.id= :customId " +
            "and c.active= true ")
    Optional<Custom> findWithMemberAndDesignerAndImgsById(@Param("customId") Long customId);

    @Query("select distinct c from Custom c " +
            "join fetch c.designer d " +
            "join fetch c.reference cr " +
            "join fetch cr.referenceImgs " +
            "join c.member m " +
            "where m.id= :memberId " +
            "and c.active= true ")
    List<Custom> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("select c from Custom c " +
            "join fetch c.reference cr " +
            "join fetch cr.referenceImgs " +
            "join c.member m " +
            "where c.id= :customId " +
            "and m.id= :memberId " +
            "and c.active= true ")
    Optional<Custom> findWithImgsById(@Param("memberId")Long memberId, @Param("customId") Long customId);

    @Query("select c from Custom c " +
            "join c.member m " +
            "where c.id= :customId " +
            "and m.id= :memberId " +
            "and c.active= true ")
    Optional<Custom> findById(@Param("memberId")Long memberId, @Param("customId") Long customId);
}
