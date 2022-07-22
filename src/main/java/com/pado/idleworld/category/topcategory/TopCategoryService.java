package com.pado.idleworld.category.topcategory;


import com.pado.idleworld.domain.TopCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TopCategoryService {

    private final TopCategoryRepository topCategoryRepository;

    @Transactional
    public Long join(TopCategory topCategory) {
        topCategoryRepository.save(topCategory);
        return topCategory.getId();
    }

    @Transactional
    public void createTopCategory(TopCategoryCreateRequest request) {
        TopCategory topCategory = TopCategory.builder()
                .title(request.getTitle())
                .imageUrl(request.getImageUrl())
                .build();
        topCategoryRepository.save(topCategory);

    }

    public List<TopCategory> findTopCategories() {
        return topCategoryRepository.findAll();
    }

    public TopCategory findOneTopCategory(Long id) {
        return topCategoryRepository.findOneById(id);
    }
    @Transactional
    public void updateTopCategory(TopCategoryUpdateRequest request) {
        TopCategory topCategory = topCategoryRepository.findOneById(request.getId());
        topCategory.setTitle(request.getTitle());
        topCategory.setImageUrl(request.getImageUrl());
    }
}
