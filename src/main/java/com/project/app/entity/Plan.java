package com.project.app.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mst_plan")
public class Plan {

    //basic premium
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String planId;

    private String plan;

    private String description;

    private Integer price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "feature_plan",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private Set<Features> features;

    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
    private Boolean isDeleted;

    @PrePersist
    private void prePersist() {
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updatedAt == null) this.updatedAt = new Date();
        if (this.isDeleted == null) this.isDeleted = false;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = new Date();
    }

    public Plan() {
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Set<Features> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Features> features) {
        this.features = features;
    }
}
