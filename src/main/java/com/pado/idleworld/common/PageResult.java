package com.pado.idleworld.common;

import lombok.Getter;

@Getter
public class PageResult extends DataResult{

    private int pageNumber;
    private int pageSize;
    private int totalPages;

    public PageResult(ResponseCode responseCode, Object data, int pageNumber, int pageSize, int totalPages) {
        super(responseCode, data);
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
    }
}
