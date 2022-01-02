package com.project.app.naufandi.dto;

public class UserDTO {
    private String SearchByUserName;
    private String SearchByUserIdentityNumber;

    public UserDTO(String searchByUserName, String searchByUserIdentityNumber) {
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
