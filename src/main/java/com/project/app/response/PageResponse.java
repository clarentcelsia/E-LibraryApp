package com.project.app.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> data;
    private String message;
    private Long totalContent;
    private Integer totalPage;
    private Integer page;
    private Integer size;
}
