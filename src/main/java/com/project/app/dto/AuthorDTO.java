package com.project.app.dto;

public class AuthorDTO {
    private String getSearchByName;

    public AuthorDTO(String getSearchByName) {
        this.getSearchByName = getSearchByName;
    }

    public String getGetSearchByName() {
        return getSearchByName;
    }

    public void setGetSearchByName(String getSearchByName) {
        this.getSearchByName = getSearchByName;
    }
}
