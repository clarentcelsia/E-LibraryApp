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
@Table(name = "client_transactions")
public class Transaction {

    //basic premium
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String transactionId;

    @ManyToOne(targetEntity = Clients.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Clients client;

    @OneToOne(targetEntity = Plan.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @JsonFormat(
            pattern = "yyyy-MM-dd hh:mm:ss"
    )
    @CreatedDate
    @Column(updatable = false)
    private Date transactionDate;

    private Integer grandtotal;

    private Boolean isDeleted;

    @PrePersist
    private void prePersist(){
        if (this.isDeleted == null) this.isDeleted = false;
        if (this.transactionDate == null) this.transactionDate = new Date();
    }
}
