package com.pado.idleworld.contents;

import com.pado.idleworld.domain.BaseCategoryContents;
import com.pado.idleworld.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BaseCategoryContentsRepository extends JpaRepository<BaseCategoryContents, Long> {

    // 전체 컨텐츠 뽑아오기
    @Query("select new com.pado.idleworld.contents.ContentsResponseDto(bcc.contents.title, bcc.contents.subtitle, bcc.contents.imageUrl)" +
            " from BaseCategoryContents bcc")
    List<ContentsResponseDto> findContentsResponseDto();


    // baseCategory 기준으로 쿼리 뽑아옴
    @Query("select new com.pado.idleworld.contents.ContentsResponseDto(bcc.contents.title, bcc.contents.subtitle, bcc.contents.imageUrl)" +
            " from BaseCategoryContents bcc" +
            " where bcc.baseCategory.id = :baseCategoryId")
    List<ContentsResponseDto> findContentsResponseDtoByBaseCategoryId(@Param("baseCategoryId") Long baseCategoryId);
}
