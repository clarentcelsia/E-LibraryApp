package com.project.app.hadiyankp.specification;

import com.project.app.hadiyankp.dto.BookDTO;
import com.project.app.hadiyankp.entity.library.Books;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<Books> getSpecification(BookDTO bookDTO){
        return new Specification<Books>() {
            @Override
            public Predicate toPredicate(Root<Books> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(bookDTO.getSearchByTitle() != null){
                    Predicate title = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" +
                            bookDTO.getSearchByTitle().toLowerCase() + "%");

                    predicates.add(title);
                }
                Predicate[]arrayPredicates =predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
