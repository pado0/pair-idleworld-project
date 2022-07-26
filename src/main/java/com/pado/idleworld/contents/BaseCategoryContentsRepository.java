package com.pado.idleworld.contents;

import com.pado.idleworld.domain.BaseCategoryContents;
import com.pado.idleworld.domain.Contents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BaseCategoryContentsRepository extends JpaRepository<BaseCategoryContents, Long> {

    // 전체 컨텐츠 뽑아오기 todo: 묵시적 조인을 명시적 조인으로 변경
    @Query("select distinct new com.pado.idleworld.contents.ContentsResponseDto(bcc.contents.id, bcc.contents.title, bcc.contents.subtitle, bcc.contents.imageUrl, bcc.contents.videoUrl)" +
            " from BaseCategoryContents bcc")
    List<ContentsResponseDto> findContentsResponseDto();

    // 전체 컨텐츠 뽑아오기 with pacing
    @Query("select distinct new com.pado.idleworld.contents.ContentsResponseDto(bcc.contents.id, bcc.contents.title, bcc.contents.subtitle, bcc.contents.imageUrl, bcc.contents.videoUrl)" +
            " from BaseCategoryContents bcc")
    Page<ContentsResponseDto> findContentsResponseDtoWithPaging(Pageable pageable);


    // baseCategory 기준으로 쿼리 뽑아옴.
    // todo: 묵시적 내부 조인을 명시적 조인으로 변경
    @Query("select new com.pado.idleworld.contents.ContentsResponseDto(bcc.contents.id, bcc.contents.title, bcc.contents.subtitle, bcc.contents.imageUrl, bcc.contents.videoUrl)" +
            " from BaseCategoryContents bcc" +
            " where bcc.baseCategory.id = :baseCategoryId")
    List<ContentsResponseDto> findContentsResponseDtoByBaseCategoryId(@Param("baseCategoryId") Long baseCategoryId);

    BaseCategoryContents findOneByContentsIdAndBaseCategoryId(Long contentsId, BaseCategoryContents baseCategoryId);
}
