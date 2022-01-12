package com.project.app.specification;

import com.project.app.dto.ReturnDTO;
import com.project.app.entity.ReturnBook;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReturnSpec {
    public static Specification<ReturnBook> getSpecification(ReturnDTO dto){
        return new Specification<ReturnBook>() {
            @Override
            public Predicate toPredicate(Root<ReturnBook> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                if(dto.getSearchByDateReturn() != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String modifiedDate = sdf.format(
                            Date.valueOf(dto.getSearchByDateReturn()));

                    Predicate dateBorrowPredicate = criteriaBuilder.equal(
                            criteriaBuilder.function("TO_CHAR", String.class, root.get("dateReturn"), criteriaBuilder.literal("yyyy-MM-dd")),
                            modifiedDate
                    );
                    predicateList.add(dateBorrowPredicate);
                }

                if(dto.getSearchByTotalPenaltyFee() != null){
                    Predicate feePredicate = criteriaBuilder.between(
                            root.get("totalPenaltyFee"),
                            0,
                            dto.getSearchByTotalPenaltyFee()
                    );
                    predicateList.add(feePredicate);
                }

                if(dto.getSearchByTotalQty() != null){
                    Predicate qtyPredicate = criteriaBuilder.between(
                            root.get("totalQty"),
                            0,
                            dto.getSearchByTotalQty()
                    );
                    predicateList.add(qtyPredicate);
                }


                Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
                Predicate predicateWithAnd = criteriaBuilder.and(predicates);
                return predicateWithAnd;
            }
        };
    }
}
