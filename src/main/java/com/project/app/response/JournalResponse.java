package com.project.app.response;

import com.project.app.entity.library.Writer;

import java.util.Set;

public class JournalResponse {
    private String doi;
    private String title;
    private String description;
    private Set<Writer> writers;

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

    public Set<Writer> getWriters() {
        return writers;
    }

    public void setWriters(Set<Writer> writers) {
        this.writers = writers;
    }

    public JournalResponse(String doi, String title, String description, Set<Writer> writers) {
        this.doi = doi;
        this.title = title;
        this.description = description;
        this.writers = writers;
    }

    public JournalResponse() {
    }

}
