package com.sctk.cmc.repository;

import com.sctk.cmc.domain.Designer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DesignerRepository {
    private final EntityManager em;

    public Long save(Designer designer) {
        em.persist(designer);
        return designer.getId();
    }

    public Optional<Designer> findByName(String name) {
        return em.createQuery("select d from Designer d where d.name = :name", Designer.class)
                .setParameter("name", name)
                .getResultStream()
                .findAny();
    }

    public Optional<Designer> findByEmail(String email) {
        return em.createQuery("select d from Designer d where d.email = :email", Designer.class)
                .setParameter("email", email)
                .getResultStream()
                .findAny();
    }
}
