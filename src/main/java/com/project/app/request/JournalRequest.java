package com.project.app.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class JournalRequest {
    private String id;
    private String doi;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
    private String files;
    private List<String> writers;

    public JournalRequest() {
    }

    public JournalRequest(String doi, String title, String description, Date publishDate, String files) {
        this.doi = doi;
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
        this.files = files;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }
}
