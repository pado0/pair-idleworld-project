package com.pado.idleworld.topcategory;

import com.pado.idleworld.domain.TopCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TopCategoryRepository {

    private final EntityManager em;

    public List<TopCategory> findAll() {
        return em.createQuery("select t from TopCategory t", TopCategory.class)
                .getResultList();
    }

    public void save(TopCategory topCategory) {
        em.persist(topCategory);
    }

    public TopCategory findOneById(Long id) {
        return em.find(TopCategory.class, id);
    }
}
