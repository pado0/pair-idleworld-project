package com.pado.idleworld.contents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

// Data Jpa 사용자 정의 쿼리 사용을 위해 컨텐츠 리스폰스만 별도의 DTO로 선언
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContentsResponseDto {

        private Long id;

        private String title;

        private String subtitle;

        private String imageUrl;

        private String videoUrl;
}
