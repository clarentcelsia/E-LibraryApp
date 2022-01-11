package com.project.app.specification;

import com.project.app.dto.LostBookDTO;
import com.project.app.entity.Book;
import com.project.app.entity.LostBookReport;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LostBookSpec {
    public static Specification<LostBookReport> getSpecification(LostBookDTO dto){
        return new Specification<LostBookReport>() {
            @Override
            public Predicate toPredicate(Root<LostBookReport> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                if(dto.getSearchByDateLost() != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String modifiedDate = sdf.format(
                            Date.valueOf(dto.getSearchByDateLost()));

                    Predicate dataLost = criteriaBuilder.equal(
                            criteriaBuilder.function("TO_CHAR", String.class, root.get("dateLost"), criteriaBuilder.literal("yyyy-MM-dd")),
                            modifiedDate
                    );
                    predicateList.add(dataLost);
                }

                if(dto.getSearchByBookId() != null){
                    Join<LostBookReport, Book> bookReportBookJoin = root.join("book");
                    Predicate bookId = criteriaBuilder.equal(
                            bookReportBookJoin.get("id"),
                            dto.getSearchByBookId()
                    );
                    predicateList.add(bookId);
                }
                Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
                Predicate predicateWithAnd = criteriaBuilder.and(predicates);
                return predicateWithAnd;
            }
        };
    }
}
