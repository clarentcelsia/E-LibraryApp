package com.project.app.specification;

import com.project.app.dto.RoomDTO;
import com.project.app.entity.Room;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RoomSpecification {
    public static Specification<Room> getSpecification(RoomDTO roomDTO){
        return new Specification<Room>() {
            @Override
            public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();


                if (roomDTO.getSearchBySubTopic() != null){
                    Predicate topicPredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("subTopic")),
                            "%" + roomDTO.getSearchBySubTopic().toLowerCase()+ "%");
                    predicateList.add(topicPredicate);
                }

                if (roomDTO.getSearchByTopic() != null){
                    Predicate topicPredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("topic")),
                            "%" + roomDTO.getSearchByTopic().toLowerCase()+ "%");
                    predicateList.add(topicPredicate);
                }

                Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
                Predicate predicateWithAnd = criteriaBuilder.and(predicates);
                return predicateWithAnd;
            }
        };
    }
}
