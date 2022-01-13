package com.project.app.dto;

public class BookDTO {
    private String searchByTitle;
    private String searchByCode;

    public BookDTO() {
    }

    public BookDTO(String searchByTitle, String searchByCode) {
        this.searchByTitle = searchByTitle;
        this.searchByCode = searchByCode;
    }

    public String getSearchByTitle() {
        return searchByTitle;
    }

    public void setSearchByTitle(String searchByTitle) {
        this.searchByTitle = searchByTitle;
    }

    public String getSearchByCode() {
        return searchByCode;
    }

    public void setSearchByCode(String searchByCode) {
        this.searchByCode = searchByCode;
    }
}
