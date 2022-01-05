package com.project.app.specification;

import com.project.app.dto.EbookAuthorDTO;
import com.project.app.entity.EbookAuthor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EbookAuthorSpecification {

    public static Specification<EbookAuthor> getSpecification(EbookAuthorDTO ebookAuthorDTO){
        return new Specification<EbookAuthor>() {
            @Override
            public Predicate toPredicate(Root<EbookAuthor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(ebookAuthorDTO.getAuthor() != null){
                    Predicate ebookAuthorPredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")), "%" +
                                    ebookAuthorDTO.getAuthor().toLowerCase() + "%");

                    predicates.add(ebookAuthorPredicate);
                }

                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
