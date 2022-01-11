package com.project.app.dto;

public class AdministratorDTO {
    private String SearchByUserName;
    private String SearchByUserIdentityNumber;

    public AdministratorDTO(String searchByUserName, String searchByUserIdentityNumber) {
        SearchByUserName = searchByUserName;
        SearchByUserIdentityNumber = searchByUserIdentityNumber;
    }

    public String getSearchByUserName() {
        return SearchByUserName;
    }

    public void setSearchByUserName(String searchByUserName) {
        SearchByUserName = searchByUserName;
    }

    public String getSearchByUserIdentityNumber() {
        return SearchByUserIdentityNumber;
    }

    public void setSearchByUserIdentityNumber(String searchByUserIdentityNumber) {
        SearchByUserIdentityNumber = searchByUserIdentityNumber;
    }
}
