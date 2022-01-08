package com.project.app.response;

import java.util.List;

public class PageResponse<T> {

    private Long count;
    private Integer totalPage;
    List<T> content;

    public PageResponse() {
    }

    public PageResponse(List<T> content, Long count, Integer totalPage) {
        this.content = content;
        this.count = count;
        this.totalPage = totalPage;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

}
