package com.sctk.cmc.repository;

import com.sctk.cmc.domain.Custom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomRepository extends JpaRepository<Custom, Long> {

    @Query("select c from Custom c " +
            "join fetch c.member " +
            "join c.designer d " +
            "where d.id= :designerId " +
            "and c.active= true ")
    List<Custom> findAllByDesignerId(@Param("designerId") Long designerId);
}
