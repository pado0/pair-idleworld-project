package com.pado.idleworld.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Agreement {

    @Id @GeneratedValue
    private Long id;

    private String title;

    @Lob
    private String subtitle;


    // DTO용 inner class 정의
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{

        @Min(value = 3)
        private String title;
        private String subtitle;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private String title;
        private String subtitle;
    }

}
