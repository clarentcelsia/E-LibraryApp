package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    private String id;

    @Column(nullable = false)
    private Integer totalQty;

    @Column(nullable = false)
    private Boolean returnStatus;

    @ManyToOne ( targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @CreatedDate
    @Column(updatable = false)
    private Date dateBorrow;

    // nanti tambahin time
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date dateDue;

    @PrePersist
    public void beforePersist(){
        if (dateBorrow == null) dateBorrow = new Date();
        if (returnStatus == null) returnStatus = false;
    }
}
