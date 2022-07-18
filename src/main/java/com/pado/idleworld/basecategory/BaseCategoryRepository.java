package com.pado.idleworld.basecategory;


import com.pado.idleworld.domain.BaseCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BaseCategoryRepository {

    private final EntityManager em;

    public void save(BaseCategory baseCategory) {
        em.persist(baseCategory);
    }

    public List<BaseCategory> findAll() {
        return em.createQuery("select b from BaseCategory b", BaseCategory.class)
                .getResultList();
    }
}
