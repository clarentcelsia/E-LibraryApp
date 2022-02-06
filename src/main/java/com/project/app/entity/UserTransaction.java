package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
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
@Table(name = "user_transaction")
public class UserTransaction {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String userTransactionId;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "customer_id")
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @CreatedDate
    @Column(updatable = false)
    private Date transactionDate;

    @OneToMany(targetEntity = UserTransactionDetail.class, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<UserTransactionDetail> transactionDetails;

    private Boolean isDeleted;

    @PrePersist
    private void prePersist() {
        if (this.transactionDate == null) this.transactionDate = new Date();
        if (this.isDeleted == null) this.isDeleted = false;
    }
}
