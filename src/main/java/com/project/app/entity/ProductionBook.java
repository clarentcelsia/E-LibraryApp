package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.app.files.Files;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

//Self-publish
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER,
    cascade = {
            CascadeType.REFRESH
    })
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
}
