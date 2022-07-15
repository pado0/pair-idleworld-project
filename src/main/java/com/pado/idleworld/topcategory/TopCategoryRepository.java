package com.pado.idleworld.topcategory;

import com.pado.idleworld.domain.TopCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TopCategoryRepository {

    private final EntityManager em;

    public void save(TopCategory topCategory) {
        em.persist(topCategory);
    }
}
