package com.project.app.hadiyankp.dto;

public class JournalDTO {
    private String getSearchByTitle;
    private String getSearchByPublishDate;

    public String getGetSearchByTitle() {
        return getSearchByTitle;
    }

    public void setGetSearchByTitle(String getSearchByTitle) {
        this.getSearchByTitle = getSearchByTitle;
    }

    public String getGetSearchByPublishDate() {
        return getSearchByPublishDate;
    }

    public void setGetSearchByPublishDate(String getSearchByPublishDate) {
        this.getSearchByPublishDate = getSearchByPublishDate;
    }

    public JournalDTO() {
    }

    public JournalDTO(String getSearchByTitle, String getSearchByPublishDate) {
        this.getSearchByTitle = getSearchByTitle;
        this.getSearchByPublishDate = getSearchByPublishDate;
    }
}
