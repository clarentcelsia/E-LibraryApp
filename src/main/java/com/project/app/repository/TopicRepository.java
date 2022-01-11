package com.project.app.repository;

import com.project.app.entity.Post;
import com.project.app.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {
    public Page<Topic> findAll(Specification<Topic> specification, Pageable pageable);
}
