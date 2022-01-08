package com.project.app.handler;

import com.project.app.entity.User;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "permission_request")
public class ResearchPermissionHandler {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String requestId;

    @ManyToOne(targetEntity = User.class)
    private User user;

    private String researchFile;

    private String comment;

    private Boolean isAccepted;

    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @PrePersist
    private void prePersist(){
        if (this.isAccepted == null) this.isAccepted = false;
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updatedAt == null) this.updatedAt = new Date();
        if (this.comment == null) this.comment = "not yet";
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = new Date();
    }

    public ResearchPermissionHandler() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getResearchFile() {
        return researchFile;
    }

    public void setResearchFile(String researchFile) {
        this.researchFile = researchFile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
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
}
