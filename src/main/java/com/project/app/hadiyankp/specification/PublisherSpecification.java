package com.project.app.hadiyankp.specification;

import com.project.app.hadiyankp.dto.PublisherDTO;
import com.project.app.hadiyankp.entity.library.Publisher;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PublisherSpecification {
    public static Specification<Publisher> getSpecification (PublisherDTO publisherDTO){
        return new Specification<Publisher>() {
            @Override
            public Predicate toPredicate(Root<Publisher> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (publisherDTO.getGetPublisherName() != null) {
                    Predicate publisherNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("publisher")), "%"
                            + publisherDTO.getGetPublisherName().toLowerCase() + "%");
                    predicates.add(publisherNamePredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
