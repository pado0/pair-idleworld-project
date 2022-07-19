package com.pado.idleworld.category.midcategory;

import com.pado.idleworld.domain.MidCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MidCategoryRepository {
    private final EntityManager em;

    public void save(MidCategory midCategory) {
        em.persist(midCategory);
    }

    public List<MidCategory> findAll() {
        return em.createQuery("select m from MidCategory m", MidCategory.class)
                .getResultList();
    }

    public MidCategory findOneById(Long id) {
        return em.find(MidCategory.class, id);
    }
}
