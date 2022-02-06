package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

//Handle by admin
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    private String previewLink;

    private String downloadLink;

    private Integer stock;
    private Integer price;

    private Boolean availableForBookPhysic;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @LastModifiedDate
    private Date updatedAt;

    @PrePersist
    private void prePersist(){
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updatedAt == null) this.updatedAt = new Date();
        if (this.price == null) this.price = 0;
        if (this.stock == null) this.stock = 0;
        if (this.availableForBookPhysic == null) this.availableForBookPhysic = false;
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = new Date();
    }
}
