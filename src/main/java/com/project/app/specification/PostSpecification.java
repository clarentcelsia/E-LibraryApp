package com.project.app.specification;

import com.project.app.dto.PostDTO;
import com.project.app.entity.Post;
import javafx.geometry.Pos;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PostSpecification {
    public static Specification<Post> getSpecification(PostDTO dto){
        return new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                if (dto.getSearchByTitle() != null){
                    predicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("title")),
                            "%" + dto.getSearchByTitle().toLowerCase()+ "%");
                }

                return predicate;
            }
        };
    }
}
