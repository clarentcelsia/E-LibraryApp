package com.project.app.dto;


import com.project.app.entity.EbookAuthor;

public class EbookDTO {

    private String title;

    public EbookDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
