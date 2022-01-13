package com.project.app.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.app.entity.library.Category;
import com.project.app.entity.library.Type;

import java.util.Date;
import java.util.List;

public class BookRequest {
    private String id;
    private String bookCode;
    private String cover;
    private String title;
    private String description;
    private List<String> authors;
    private String publisher;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd"
    )
    private Date publishedDate;
    private String bookUrl;
    private Integer stock;
    private Category category; //book/magazine
    private Type type; //novel/comic/non-fiction/others
    private Float totalRating;
    private Integer totalReview;

    public BookRequest() {
    }

    public BookRequest(String id, String cover, String title, String description, List<String> authors, String publisher, Date publishedDate, String book, Integer stock, Category category, Type type, Float totalRating, Integer totalReview) {
        this.id = id;
        this.cover = cover;
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.bookUrl = book;
        this.stock = stock;
        this.category = category;
        this.type = type;
        this.totalRating = totalRating;
        this.totalReview = totalReview;
    }

    public BookRequest(String id, String cover, String title, String description, String publisher, Date publishedDate, String bookUrl, Integer stock, Category category, Type type, Float totalRating, Integer totalReview) {
        this.id = id;
        this.cover = cover;
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.bookUrl = bookUrl;
        this.stock = stock;
        this.category = category;
        this.type = type;
        this.totalRating = totalRating;
        this.totalReview = totalReview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Float getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Float totalRating) {
        this.totalRating = totalRating;
    }

    public Integer getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(Integer totalReview) {
        this.totalReview = totalReview;
    }

}
