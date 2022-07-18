package com.pado.idleworld.basecategory;


import com.pado.idleworld.domain.BaseCategory;
import com.pado.idleworld.domain.MidCategory;
import com.pado.idleworld.midcategory.MidCategoryRepository;
import com.pado.idleworld.topcategory.TopCategoryRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Lob;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BaseCategoryService {

    private final BaseCategoryRepository baseCategoryRepository;
    private final MidCategoryRepository midCategoryRepository;
    private final TopCategoryRepository topCategoryRepository;

    @Transactional
    public void createBaseCategory(BaseCategoryCreateRequest request) {
        MidCategory findMidCategory = midCategoryRepository.findOneById(request.getMidCategoryId());
        BaseCategory baseCategory = BaseCategory.builder()
                .title(request.getTitle())
                .imageUrl(request.getImageUrl())
                .midCategory(findMidCategory)
                .build();
        baseCategoryRepository.save(baseCategory);
    }

    public List<BaseCategoryReadResponse> findBaseCategories() {
        return baseCategoryRepository.findAll().stream()
                .map(m-> new BaseCategoryReadResponse(m.getId(),m.getTitle(),m.getImageUrl(),m.getMidCategory().getId()))
                .collect(Collectors.toList());
    }
}
