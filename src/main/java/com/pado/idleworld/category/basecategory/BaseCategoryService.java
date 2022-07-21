package com.pado.idleworld.category.basecategory;


import com.pado.idleworld.category.midcategory.MidCategoryRepository;
import com.pado.idleworld.category.topcategory.TopCategoryRepository;
import com.pado.idleworld.domain.BaseCategory;
import com.pado.idleworld.domain.MidCategory;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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

    @Transactional
    public void updateBaseCategory(BaseCategoryUpdateRequest request) {
        Optional<BaseCategory> findBaseCategoryOptional = baseCategoryRepository.findById(request.getId());

        if(findBaseCategoryOptional.isEmpty()) return; // 예외처리 필요

        BaseCategory findBaseCategory = findBaseCategoryOptional.get();
        MidCategory findMidCategory = midCategoryRepository.findOneById(request.getMidCategoryId());

        findBaseCategory.setTitle(request.getTitle());
        findBaseCategory.setImageUrl(request.getImageUrl());
        findBaseCategory.setMidCategory(findMidCategory);

    }
}
