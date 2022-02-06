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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mst_client")
public class Clients {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String clientId;

    private String name;
    private String address;
    private String phoneNumber;
    private String email;

//    @OneToOne(fetch = FetchType.EAGER, targetEntity = Plan.class, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private Plan plan;

    //active/inactive
    private Integer status;

//    @OneToOne(fetch = FetchType.EAGER, targetEntity = Slot.class, cascade = CascadeType.REMOVE)
//    private Slot slot;

    @CreatedDate
    @Column(updatable = false)
    @JsonIgnore
    private Date createdAt;

    @LastModifiedDate
    @JsonIgnore
    private Date updatedAt;
    private Boolean isDeleted;

    @PrePersist
    private void prePersist(){
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updatedAt == null) this.updatedAt = new Date();
        if (this.isDeleted == null) this.isDeleted = false;
        if (this.status == null) this.status = 1;
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = new Date();
    }

//    public Plan getPlan() {
//        return plan;
//    }
//
//    public void setPlan(Plan plan) {
//        this.plan = plan;
//    }

//    public Slot getSlot() {
//        return slot;
//    }
//
//    public void setSlot(Slot slot) {
//        this.slot = slot;
//    }
}
