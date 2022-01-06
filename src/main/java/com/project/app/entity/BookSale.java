package com.project.app.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mst_book_sale")
public class BookSale {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String bookSaleId;

    private String imageUrl;
    private String title;
    private String description;

    @Column(updatable = false)
    private String previewLink;

    @Column(updatable = false)
    private String downloadLink;

    private Integer stock;
    private Integer price;
    @Column(updatable = false)
    private String createdBy;
    private Boolean availableForBookPhysic;

    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @PrePersist
    private void prePersist(){
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updatedAt == null) this.updatedAt = new Date();
        if (this.stock == null) this.stock = 0;
        if (this.availableForBookPhysic == null) this.availableForBookPhysic = false;
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = new Date();
    }

    public BookSale() {
    }

    public BookSale(String bookSaleId, String imageUrl, String title, String description, String previewLink, String downloadLink, Integer stock, Integer price, String createdBy, Boolean availableForBookPhysic) {
        this.bookSaleId = bookSaleId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.previewLink = previewLink;
        this.downloadLink = downloadLink;
        this.stock = stock;
        this.price = price;
        this.createdBy = createdBy;
        this.availableForBookPhysic = availableForBookPhysic;
    }

    public String getBookSaleId() {
        return bookSaleId;
    }

    public void setBookSaleId(String bookSaleId) {
        this.bookSaleId = bookSaleId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getAvailableForBookPhysic() {
        return availableForBookPhysic;
    }

    public void setAvailableForBookPhysic(Boolean availableForBookPhysic) {
        this.availableForBookPhysic = availableForBookPhysic;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
