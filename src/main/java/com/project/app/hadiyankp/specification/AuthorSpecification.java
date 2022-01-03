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
                if (authorDTO.getGetSearchByFirstName() != null) {
                    Predicate authorFirstName = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%"
                            + authorDTO.getGetSearchByFirstName().toLowerCase() + "%");
                    predicates.add(authorFirstName);
                }
                if (authorDTO.getGetSearchByMiddleName()!=null){
                    Predicate authorMiddleName = criteriaBuilder.like(criteriaBuilder.lower(root.get("middleName")), "%"
                            + authorDTO.getGetSearchByMiddleName().toLowerCase() + "%");
                    predicates.add(authorMiddleName);
                }
                if (authorDTO.getGetSearchByLastName()!=null){
                    Predicate authorLastName = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%"
                            + authorDTO.getGetSearchByLastName().toLowerCase() + "%");
                    predicates.add(authorLastName);
                }
                Predicate[]arrayPredicates =predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
