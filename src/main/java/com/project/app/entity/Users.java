package com.project.app.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//SAMPLE
@Entity
@Table(name = "mst_user")
public class Users {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String userId;

    private String name;

    //...//


    public Users() {
    }

    public Users(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
