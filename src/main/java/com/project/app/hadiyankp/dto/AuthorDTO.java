package com.project.app.hadiyankp.dto;

public class AuthorDTO {
    private String getSearchByFirstName;
    private String getSearchByMiddleName;
    private String getSearchByLastName;

    public AuthorDTO(String getSearchByFirstName, String getSearchByMiddleName, String getSearchByLastName) {
        this.getSearchByFirstName = getSearchByFirstName;
        this.getSearchByMiddleName = getSearchByMiddleName;
        this.getSearchByLastName = getSearchByLastName;
    }

    public String getGetSearchByFirstName() {
        return getSearchByFirstName;
    }

    public void setGetSearchByFirstName(String getSearchByFirstName) {
        this.getSearchByFirstName = getSearchByFirstName;
    }

    public String getGetSearchByMiddleName() {
        return getSearchByMiddleName;
    }

    public void setGetSearchByMiddleName(String getSearchByMiddleName) {
        this.getSearchByMiddleName = getSearchByMiddleName;
    }

    public String getGetSearchByLastName() {
        return getSearchByLastName;
    }

    public void setGetSearchByLastName(String getSearchByLastName) {
        this.getSearchByLastName = getSearchByLastName;
    }

    public AuthorDTO() {
    }
}
