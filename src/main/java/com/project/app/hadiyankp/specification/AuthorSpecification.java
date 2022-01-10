package com.project.app.hadiyankp.specification;

import com.project.app.hadiyankp.dto.AuthorDTO;
import com.project.app.hadiyankp.entity.library.Author;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AuthorSpecification {
    public static Specification<Author> getSpecification(AuthorDTO authorDTO) {
        return new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (authorDTO.getGetSearchByName() != null) {
                    Predicate name = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"
                            + authorDTO.getGetSearchByName().toLowerCase() + "%");
                    predicates.add(name);
                }

                Predicate[]arrayPredicates =predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
