package com.project.app.hadiyankp.response;

import com.project.app.hadiyankp.entity.library.Writer;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class JournalResponse {
    private String doi;
    private String title;
    private String description;
    private List<Writer> writers = new ArrayList<>();

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

    public List<Writer> getWriters() {
        return writers;
    }

    public void setWriters(List<Writer> writers) {
        this.writers = writers;
    }

    public JournalResponse(String doi, String title, String description, List<Writer> writers) {
        this.doi = doi;
        this.title = title;
        this.description = description;
        this.writers = writers;
    }

    public JournalResponse() {
    }

}
