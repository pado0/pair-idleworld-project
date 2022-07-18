package com.pado.idleworld.midcategory;

import com.pado.idleworld.domain.MidCategory;
import com.pado.idleworld.domain.TopCategory;
import com.pado.idleworld.topcategory.TopCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Lob;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MidCategoryService {

    private final MidCategoryRepository midCategoryRepository;
    private final TopCategoryRepository topCategoryRepository;

    @Transactional
    public void createMidCategory(MidCategoryCreateRequest request) {
        TopCategory findTopCategory = topCategoryRepository.findOneById(request.getTopCategoryId());

        MidCategory midCategory = MidCategory.builder()
                .title(request.getTitle())
                .imageUrl(request.getImageUrl())
                .videoUrl(request.getVideoUrl())
                .videoText(request.getVideoText())
                .topCategory(findTopCategory)
                .build();

        midCategoryRepository.save(midCategory);
    }

    public List<MidCategory> findMidCategories() {
        return midCategoryRepository.findAll();
    }

    @Transactional
    public void updateMidCategory(MidCategoryUpdateRequest request) {
        MidCategory findMidCategory = midCategoryRepository.findOneById(request.getId());
        TopCategory findTopCategory = topCategoryRepository.findOneById(request.getTopCategoryId());
        findMidCategory.setTitle(request.getTitle());
        findMidCategory.setImageUrl(request.getImageUrl());
        findMidCategory.setVideoUrl(request.getVideoUrl());
        findMidCategory.setVideoText(request.getVideoText());
        findMidCategory.setTopCategory(findTopCategory);
    }
}
