package com.pado.idleworld.category.basecategory;

import com.pado.idleworld.domain.BaseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseCategoryRepository extends JpaRepository<BaseCategory, Long> {
}
