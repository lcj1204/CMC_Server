package com.sctk.cmc.repository;

import com.sctk.cmc.domain.Designer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DesignerRepository {
    private final EntityManager em;

    public Long save(Designer designer) {
        em.persist(designer);
        return designer.getId();
    }

    public Optional<Designer> findById(Long id) {
        return Optional.ofNullable(em.find(Designer.class, id));
    }

    public List<Designer> findAllByName(String name) {
        return em.createQuery("select d from Designer d where d.name = :name", Designer.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Optional<Designer> findByEmail(String email) {
        return em.createQuery("select d from Designer d where d.email = :email", Designer.class)
                .setParameter("email", email)
                .getResultStream()
                .findAny();
    }
}
