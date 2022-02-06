package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admin_transaction")
public class AdminTransaction {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String transactionId;

    @ManyToOne(
            targetEntity = User.class, fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "user_id")
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @CreatedDate
    @Column(updatable = false)
    private Date transactionDate;

    @OneToMany(targetEntity = AdminTransactionDetail.class, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<AdminTransactionDetail> transactionDetails;

    private Boolean isDeleted;

    @PrePersist
    private void prePersist() {
        if (this.transactionDate == null) this.transactionDate = new Date();
        if (this.isDeleted == null) this.isDeleted = false;
    }
}
