package com.project.app.hadiyankp.specification;

import com.project.app.hadiyankp.dto.GenreDTO;
import com.project.app.hadiyankp.entity.library.Genre;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class GenreSpecification {
    public static Specification<Genre> getSpecification(GenreDTO genreDTO){
        return new Specification<Genre>() {
            @Override
            public Predicate toPredicate(Root<Genre> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (genreDTO.getGetSearchByGenre() != null) {
                    Predicate genreNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("genre")), "%"
                            + genreDTO.getGetSearchByGenre().toLowerCase() + "%");
                    predicates.add(genreNamePredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
