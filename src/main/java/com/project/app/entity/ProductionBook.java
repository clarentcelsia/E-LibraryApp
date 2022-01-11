package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.app.files.Files;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

//Self-publish
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
    private String previewLink;
    private String downloadedLink;
    private Integer price;
    private Integer stock;
    private Boolean onSale;
    private Boolean availableForBookPhysic;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @LastModifiedDate
    private Date updatedAt;

    @PrePersist
    private void prePersist() {
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updatedAt == null) this.updatedAt = new Date();
        if (this.onSale == null) this.onSale = false;
        if (this.price == null) this.price = 0;
        if (this.stock == null) this.stock = 0;
        if (this.availableForBookPhysic == null) this.availableForBookPhysic = false;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = new Date();
        if(!this.onSale) this.availableForBookPhysic = false;
    }

    public ProductionBook() {
    }

    public ProductionBook(String productionBookId, String title) {
        this.productionBookId = productionBookId;
        this.title = title;
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

    public Boolean getAvailableForBookPhysic() {
        return availableForBookPhysic;
    }

    public void setAvailableForBookPhysic(Boolean availableForBookPhysic) {
        this.availableForBookPhysic = availableForBookPhysic;
    }

    @Override
    public String toString() {
        return "ProductionBook{" +
                "productionBookId='" + productionBookId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", description='" + description + '\'' +
                ", previewLink='" + previewLink + '\'' +
                ", downloadedLink='" + downloadedLink + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", onSale=" + onSale +
                ", user=" + user +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
