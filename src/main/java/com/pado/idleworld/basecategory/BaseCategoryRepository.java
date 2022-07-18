package com.pado.idleworld.basecategory;


import com.pado.idleworld.domain.BaseCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BaseCategoryRepository {

    private final EntityManager em;

    public void save(BaseCategory baseCategory) {
        em.persist(baseCategory);
    }
}
