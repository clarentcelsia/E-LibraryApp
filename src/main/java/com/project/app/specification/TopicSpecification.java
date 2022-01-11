package com.project.app.specification;

import com.project.app.dto.TopicDTO;
import com.project.app.entity.Topic;
import com.project.app.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class TopicSpecification {
    public static Specification<Topic> getSpecificationTopic(TopicDTO dto){
        return new Specification<Topic>() {
            @Override
            public Predicate toPredicate(Root<Topic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                if (dto.getSearchByTopicSubject() != null){
                    Predicate topicPredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("topicSubject")),
                            "%" + dto.getSearchByTopicSubject().toLowerCase()+ "%");
                    predicateList.add(topicPredicate);
                }


                if (dto.getSearchByUserName() != null){
                    Join<Topic, User> topicUserJoin = root.join("user");
                    Predicate userPredicate = criteriaBuilder.equal(
                            criteriaBuilder.lower(topicUserJoin.get("name")),
                            dto.getSearchByUserName());

                    predicateList.add(userPredicate);
                }
                Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
                Predicate predicateWithAnd = criteriaBuilder.and(predicates);
                return predicateWithAnd;
            }
        };
    }
}
