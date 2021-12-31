package com.project.app.hadiyankp.dto;

public class SubjectDTO {
    private String searchBySubjectName;

    public String getSearchBySubjectName() {
        return searchBySubjectName;
    }

    public void setSearchBySubjectName(String searchBySubjectName) {
        this.searchBySubjectName = searchBySubjectName;
    }

    public SubjectDTO() {
    }

    public SubjectDTO(String searchBySubjectName) {
        this.searchBySubjectName = searchBySubjectName;
    }
}
