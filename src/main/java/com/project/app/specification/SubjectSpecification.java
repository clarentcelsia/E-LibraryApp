package com.project.app.specification;

import com.project.app.dto.SubjectDTO;
import com.project.app.entity.library.Subject;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class SubjectSpecification {
    public static Specification<Subject> getSpecification(SubjectDTO subjectDTO) {
        return new Specification<Subject>() {
            @Override
            public Predicate toPredicate(Root<Subject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (subjectDTO.getSearchBySubjectName() != null) {
                    Predicate subjectNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("subjectName")), "%"
                            + subjectDTO.getSearchBySubjectName().toLowerCase() + "%");
                    predicates.add(subjectNamePredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
