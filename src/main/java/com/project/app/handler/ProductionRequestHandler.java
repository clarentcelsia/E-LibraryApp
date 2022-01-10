package com.project.app.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.app.entity.ProductionBook;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "production_request")
public class ProductionRequestHandler {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String productionRequestHandlerId;

    @OneToOne(targetEntity = ProductionBook.class)
    @JoinColumn(name = "production_book_id")
    private ProductionBook productionBook;

    //Jika buku habis atau lagi ada kendala admin bisa menonactivekan status penjualan
    private Boolean onHandle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(updatable = false)
    @CreatedDate
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @LastModifiedDate
    private Date modifiedDate;

    @PrePersist
    private void prePersist(){
        if (this.createdAt == null) createdAt = new Date();
        if (this.modifiedDate == null) modifiedDate = new Date();
        if (this.onHandle == null) onHandle = true;
    }

    @PreUpdate
    private void preUpdate(){
        this.modifiedDate = new Date();
    }

    public ProductionRequestHandler() {
    }

    public String getProductionRequestHandlerId() {
        return productionRequestHandlerId;
    }

    public void setProductionRequestHandlerId(String productionRequestHandlerId) {
        this.productionRequestHandlerId = productionRequestHandlerId;
    }

    public ProductionBook getProductionBook() {
        return productionBook;
    }

    public void setProductionBook(ProductionBook productionBook) {
        this.productionBook = productionBook;
    }

    public Boolean getOnHandle() {
        return onHandle;
    }

    public void setOnHandle(Boolean onHandle) {
        this.onHandle = onHandle;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
