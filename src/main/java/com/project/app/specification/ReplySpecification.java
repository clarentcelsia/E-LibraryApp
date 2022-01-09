package com.project.app.specification;

import com.project.app.dto.ReplyDTO;
import com.project.app.entity.Reply;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ReplySpecification {
    public static Specification<Reply> getSpecification(ReplyDTO dto){
        return new Specification<Reply>() {
            @Override
            public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                if (dto.getSearchByMessage() != null){
                    predicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("message")),
                            "%" + dto.getSearchByMessage().toLowerCase()+ "%");
                }

                return predicate;
            }
        };
    }
}
