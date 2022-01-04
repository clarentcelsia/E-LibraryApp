package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "return_book")
public class ReturnBook {
    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    private String id;

    @OneToOne(targetEntity = Loan.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id", updatable = false, unique = true )
    private Loan loan;

    @OneToMany(targetEntity = ReturnBookDetail.class, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ReturnBookDetail> returnBookDetails;

    @Column (nullable = false)
    private Integer totalQty;

    @Column (nullable = false)
    private Date dateReturn;

    @Column (nullable = false)
    private Integer totalPenaltyFee;

    @Column (nullable = false)
    private Integer overdueFee;

    @PrePersist
    public void createdAt(){
        if (dateReturn == null) dateReturn = new Date();
    }

    @Override
    public String toString() {
        return "ReturnBook{" +
                "id='" + id + '\'' +
                ", loan=" + loan +
                ", returnBookDetails=" + returnBookDetails +
                ", totalQty=" + totalQty +
                ", dateReturn=" + dateReturn +
                ", totalPenaltyFee=" + totalPenaltyFee +
                ", overdueFee=" + overdueFee +
                '}';
    }
}
