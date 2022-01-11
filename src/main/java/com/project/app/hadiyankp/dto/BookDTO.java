package com.project.app.hadiyankp.dto;

public class BookDTO {
    private String searchByTitle;
    private String searchByYears;

    public BookDTO() {
    }

    public BookDTO(String searchByTitle) {
        this.searchByTitle = searchByTitle;
    }

    public String getSearchByTitle() {
        return searchByTitle;
    }

    public void setSearchByTitle(String searchByTitle) {
        this.searchByTitle = searchByTitle;
    }
}
