package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import net.bytebuddy.asm.Advice;
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

    @OneToMany(targetEntity = LoanDetail.class, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<LoanDetail> loanDetail;

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

    @Override
    public String toString() {
        return "Loan{" +
                "id='" + id + '\'' +
                ", totalQty=" + totalQty +
                ", returnStatus=" + returnStatus +
                ", user=" + user +
                ", loanDetail=" + loanDetail +
                ", dateBorrow=" + dateBorrow +
                ", dateDue=" + dateDue +
                '}';
    }
}
