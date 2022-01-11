package com.project.app.specification;

import com.project.app.dto.LoanDTO;
import com.project.app.entity.Loan;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LoanSpec {
    public static Specification<Loan> getSpecification(LoanDTO dto){
        return new Specification<Loan>() {
            @Override
            public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                if(dto.getSearchByDateBorrow() != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String modifiedDate = sdf.format(
                            Date.valueOf(dto.getSearchByDateBorrow()));

                    Predicate dateBorrowPredicate = criteriaBuilder.equal(
                            criteriaBuilder.function("TO_CHAR", String.class, root.get("dateBorrow"), criteriaBuilder.literal("yyyy-MM-dd")),
                            modifiedDate
                    );
                    predicateList.add(dateBorrowPredicate);
                }

                if(dto.getSearchByStatus() != null){
                    Boolean status = Boolean.valueOf(dto.getSearchByStatus());

                    Predicate statusPredicate = criteriaBuilder.equal(
                            root.get("returnStatus"),
                            status
                    );
                    predicateList.add(statusPredicate);
                }
                Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
                Predicate predicateWithAnd = criteriaBuilder.and(predicates);
                return predicateWithAnd;
            }
        };
    }
}
