package com.project.app.response;

import java.util.List;

public class PageResponse<T> {
    List<T> content;

    private Long count;

    private Integer totalPage;

    private Integer page;

    private Integer size;

    public PageResponse() {
    }

    public PageResponse(List<T> content, Long count, Integer totalPage, Integer page, Integer size) {
        this.content = content;
        this.count = count;
        this.totalPage = totalPage;
        this.page = page;
        this.size = size;
    }
}
