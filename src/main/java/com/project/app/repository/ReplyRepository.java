package com.project.app.repository;

import com.project.app.entity.Post;
import com.project.app.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    public Page<Reply> findAll(Specification<Reply> specification, Pageable pageable);
}