package com.pado.idleworld.domain;

public enum AccountRole {
    ROLE_USER,ROLE_ADMIN
    //스프링 시큐리티는 자동적으로 앞에 접미사 "ROLE_" 붙인다. 따라서 DB도 일치화 해주자.
}
