package com.project.app.request;

import com.project.app.entity.User;

import java.util.List;

public class EbookAPI {
    private String ebookCode;
    private String title;
    private List<String> authors;
    private String publishedDate;
    private String publisher;
    private String description;
    private String imageLinks;
    private String webReaderLink;
    private List<User> user;

    public EbookAPI() {
    }

    public EbookAPI(String ebookCode, String title) {
        this.ebookCode = ebookCode;
        this.title = title;
    }

    public EbookAPI(String ebookCode, String title, List<String> authors, String publishedDate, String publisher, String description, String imageLinks, String webReaderLink) {
        this.ebookCode = ebookCode;
        this.title = title;
        this.authors = authors;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.description = description;
        this.imageLinks = imageLinks;
        this.webReaderLink = webReaderLink;
    }

    public String getEbookCode() {
        return ebookCode;
    }

    public void setEbookCode(String ebookCode) {
        this.ebookCode = ebookCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(String imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getWebReaderLink() {
        return webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
