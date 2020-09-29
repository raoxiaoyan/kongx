package com.kongx.common.core.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PaginationQC implements Serializable {

    @Override
    public String toString() {
        return "PaginationQC [page=" + page + ", start=" + start + ", limit=" + limit + "]";
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        if (start <= 0) {
            start = 0;
        }
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }


    private Integer page = 0;
    private Integer start = 0;
    private Integer limit = 10;
    private String sort;

}
