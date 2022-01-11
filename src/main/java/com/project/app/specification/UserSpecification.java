package com.project.app.specification;

import com.project.app.dto.UserDTO;
import com.project.app.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public UserSpecification() {
    }

    public static Specification<User> getSpecification(UserDTO userDTO){
        return new Specification<User>() {
//            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (userDTO.getSearchByUserName() != null){
                    Predicate userNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + userDTO.getSearchByUserName().toLowerCase() + "%");
                    predicates.add(userNamePredicate);
                }

                if (userDTO.getSearchByUserIdentityNumber() != null){
                    Predicate userIdentityNumberPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("identitynumber")), "%" + userDTO.getSearchByUserIdentityNumber().toLowerCase() + "%");
                    predicates.add(userIdentityNumberPredicate);
                }

                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
