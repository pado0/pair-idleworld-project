package com.pado.idleworld.contents;

import com.pado.idleworld.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, Long> {

    @Query("select c from Contents c" +
            " join fetch c.baseCategoryContents bc" +
            " join fetch bc.baseCategory b")
    public List<Contents> findAllByBaseCategoryId(Long categoryId);

}
