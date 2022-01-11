package com.project.app.hadiyankp.dto;

public class TypeDTO {
    private String searchByTypeName;

    public TypeDTO(String searchByTypeName) {
        this.searchByTypeName = searchByTypeName;
    }

    public TypeDTO() {
    }

    public String getSearchByTypeName() {
        return searchByTypeName;
    }

    public void setSearchByTypeName(String searchByTypeName) {
        this.searchByTypeName = searchByTypeName;
    }
}
