package com.project.app.dto.ebook;

import java.util.List;

public class VolumeInfo{
    public String title;
    public List<String> authors;
    public String publishedDate;
    public List<IndustryIdentifier> industryIdentifiers;
    public ReadingModes readingModes;
    public int pageCount;
    public String printType;
    public String maturityRating;
    public boolean allowAnonLogging;
    public String contentVersion;
    public String language;
    public String previewLink;
    public String infoLink;
    public String canonicalVolumeLink;
    public String publisher;
    public String description;
    public List<String> categories;
    public PanelizationSummary panelizationSummary;
    public ImageLinks imageLinks;
    public String subtitle;
    public int averageRating;
    public int ratingsCount;

    public VolumeInfo() {
    }


}
