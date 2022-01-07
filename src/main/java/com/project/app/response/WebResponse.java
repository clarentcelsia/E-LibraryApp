package com.project.app.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WebResponse<T> {
    private String message;
    private T data;
}
