package com.project.app.hadiyankp.specification;

import com.project.app.hadiyankp.dto.JournalDTO;
import com.project.app.hadiyankp.entity.library.Journal;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JournalSpecification {
    public static Specification<Journal> getSpecification(JournalDTO journalDTO){
        return new Specification<Journal>() {
            @Override
            public Predicate toPredicate(Root<Journal> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (journalDTO.getGetSearchByTitle()!=null){
                    Predicate journalTitlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%"
                            + journalDTO.getGetSearchByTitle().toLowerCase() + "%");
                    predicates.add(journalTitlePredicate);

//                  sama seperti "SELECT * FROM mst_customer WHERE name LIKE '%(keyword/nama)'";
                }
                if (journalDTO.getGetSearchByPublishDate()!=null){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
//
                    String modifiedDateFormat = simpleDateFormat.format(Date.valueOf(journalDTO.getGetSearchByPublishDate()));
                    Predicate journalBirthDatePredicate = criteriaBuilder.equal(
                            criteriaBuilder.function("TO_CHAR", String.class, root.get("publishDate"),
                                    criteriaBuilder.literal("yyyy-MM-dd")),
                            modifiedDateFormat);
                    predicates.add(journalBirthDatePredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
