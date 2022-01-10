package com.project.app.specification;

import com.project.app.dto.ClientDTO;
import com.project.app.entity.Clients;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ClientSpecification {

    public static Specification<Clients> getSpecification(ClientDTO clientDTO){
        return new Specification<Clients>() {
            @Override
            public Predicate toPredicate(Root<Clients> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(clientDTO.getStatus() != null){
                    Expression<String> from = root.get("status");
                    Predicate predicate = criteriaBuilder.equal(from, clientDTO.getStatus());
                    predicates.add(predicate);
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
