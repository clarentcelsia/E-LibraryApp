package com.project.app.specification;

import com.project.app.dto.CategoryDTO;
import com.project.app.entity.library.Category;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CategorySpecification {
    public static Specification<Category> getSpecification(CategoryDTO categoryDTO){
        return new Specification<Category>() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (categoryDTO.getGetSearchByCategoryName() != null) {
                    Predicate categoryNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("categoryName")), "%"
                            + categoryDTO.getGetSearchByCategoryName().toLowerCase() + "%");
                    predicates.add(categoryNamePredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
