package com.project.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.project.app.utils.DefaultLocalDateTimeDeserializer;
import lombok.*;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @OneToMany(targetEntity = LoanDetail.class, fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<LoanDetail> loanDetail;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false)
    private LocalDateTime dateBorrow;

    // nanti tambahin time
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = DefaultLocalDateTimeDeserializer.class)
    @Column(nullable = false)
    private LocalDateTime dateDue;

    @PrePersist
    public void beforePersist(){
        if (dateBorrow == null) dateBorrow = LocalDateTime.now();
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
