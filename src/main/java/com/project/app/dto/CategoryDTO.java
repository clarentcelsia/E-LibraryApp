package com.project.app.dto;

public class CategoryDTO {
    private String getSearchByCategoryName;

    public CategoryDTO(String getSearchByCategoryName) {
        this.getSearchByCategoryName = getSearchByCategoryName;
    }

    public CategoryDTO() {
    }

    public String getGetSearchByCategoryName() {
        return getSearchByCategoryName;
    }

    public void setGetSearchByCategoryName(String getSearchByCategoryName) {
        this.getSearchByCategoryName = getSearchByCategoryName;
    }
}
