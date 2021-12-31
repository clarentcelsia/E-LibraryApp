package com.project.app.dto;

public class BookDTO {
    private String searchByAuthorName;
    private String searchByYears;

    public String getSearchByAuthorName() {
        return searchByAuthorName;
    }

    public void setSearchByAuthorName(String searchByAuthorName) {
        this.searchByAuthorName = searchByAuthorName;
    }

    public String getSearchByYears() {
        return searchByYears;
    }

    public void setSearchByYears(String searchByYears) {
        this.searchByYears = searchByYears;
    }

    public BookDTO() {
    }

    public BookDTO(String searchByAuthorName, String searchByYears) {
        this.searchByAuthorName = searchByAuthorName;
        this.searchByYears = searchByYears;
    }
}
