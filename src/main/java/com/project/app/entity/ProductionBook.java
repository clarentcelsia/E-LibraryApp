package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.app.files.Files;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "mst_production_book")
public class ProductionBook {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String productionBookId;
    private String imageUrl;
    private String title;
    private String subtitle;
    private String description;
    @Column(nullable = false)
    private String previewLink;
    private String downloadedLink;
    private Integer price;
    private Integer stock;
    private Boolean onSale;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(targetEntity = Files.class, fetch = FetchType.LAZY)
    private Set<Files> userFiles;

    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @PrePersist
    private void prePersist(){
        if(this.createdAt == null) this.createdAt = new Date();
        if(this.updatedAt == null) this.updatedAt = new Date();
        if(this.onSale == null) this.onSale = false;
        if(this.price == null) this.price = 0;
        if(this.stock == null) this.stock = 0;
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = new Date();
    }

    public ProductionBook() {
    }

    public String getProductionBookId() {
        return productionBookId;
    }

    public void setProductionBookId(String productionBookId) {
        this.productionBookId = productionBookId;
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
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

    public String getDownloadedLink() {
        return downloadedLink;
    }

    public void setDownloadedLink(String downloadedLink) {
        this.downloadedLink = downloadedLink;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Files> getUserFiles() {
        return userFiles;
    }

    public void setUserFiles(Set<Files> userFiles) {
        this.userFiles = userFiles;
    }
}
