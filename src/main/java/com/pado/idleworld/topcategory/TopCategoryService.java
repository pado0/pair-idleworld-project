package com.pado.idleworld.topcategory;


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
}
