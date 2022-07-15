package com.pado.idleworld.topcategory;


import com.pado.idleworld.domain.TopCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public TopCategory createTopCategory(TopCategoryCreateRequest topCategoryCreateRequest) {
        TopCategory topCategory = TopCategory.builder()
                .title(topCategoryCreateRequest.getTitle())
                .imageUrl(topCategoryCreateRequest.getImageUrl())
                .build();
        topCategoryRepository.save(topCategory);
        return topCategory;
    }
}
