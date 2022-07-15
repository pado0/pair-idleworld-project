package com.pado.idleworld.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

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
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        private String title;
        private String subtitle;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private String title;
    }

}
