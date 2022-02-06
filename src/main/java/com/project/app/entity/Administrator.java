package com.project.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mst_administrator")
public class Administrator {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String identityNumber;

    private String name;

    private String email;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String username;

    private String password;

    private Boolean status;

    private Boolean isDeleted;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "admin_role",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @PrePersist
    public void createdDate(){
        if (createdAt == null) createdAt = new Date();
        if (updatedAt == null) updatedAt = new Date();
        if (isDeleted == null) isDeleted = false;
    }

    @PreUpdate
    public void updateDate(){
        updatedAt = new Date();
    }
}
