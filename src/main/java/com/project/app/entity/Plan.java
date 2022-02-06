package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "feature_plan",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<Features> features;

    @CreatedDate
    @JsonIgnore
    @Column(updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @JsonIgnore
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
}
