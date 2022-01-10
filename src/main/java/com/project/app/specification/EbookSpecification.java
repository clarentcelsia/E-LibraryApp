package com.project.app.specification;

import com.project.app.dto.EbookDTO;
import com.project.app.entity.Ebook;
import com.project.app.entity.EbookAuthor;
import com.project.app.service.EbookAuthorService;
import com.project.app.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class EbookSpecification {

    public static Specification<Ebook> getSpecification(EbookDTO ebookDTO){
        return new Specification<Ebook>() {
            @Override
            public Predicate toPredicate(Root<Ebook> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(ebookDTO.getTitle() != null){
                    Predicate ebookTitlePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("title")), "%" +
                                    ebookDTO.getTitle().toLowerCase() + "%");

                    predicates.add(ebookTitlePredicate);
                }

                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }

}
