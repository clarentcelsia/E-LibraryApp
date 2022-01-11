package com.project.app.hadiyankp.specification;

import com.project.app.hadiyankp.dto.TypeDTO;
import com.project.app.hadiyankp.entity.library.Type;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TypeSpecification {
    public static Specification<Type>getSpecification(TypeDTO typeDTO){
        return new Specification<Type>() {
            @Override
            public Predicate toPredicate(Root<Type> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (typeDTO.getSearchByTypeName() != null) {
                    Predicate typeNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("type")), "%"
                            + typeDTO.getSearchByTypeName().toLowerCase() + "%");
                    predicates.add(typeNamePredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
