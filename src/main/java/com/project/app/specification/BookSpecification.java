package com.project.app.specification;

import com.project.app.dto.BookDTO;
import com.project.app.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<Book> getSpecification(BookDTO bookDTO){
        return new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(bookDTO.getSearchByTitle() != null){
                    Predicate title = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" +
                            bookDTO.getSearchByTitle().toLowerCase() + "%");

                    predicates.add(title);
                }

                if (bookDTO.getSearchByCode() != null){
                    Predicate code = criteriaBuilder.like(criteriaBuilder.lower(root.get("bookCode")), "%" +
                            bookDTO.getSearchByCode().toLowerCase() + "%");

                    predicates.add(code);
                }
                Predicate[]arrayPredicates =predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
