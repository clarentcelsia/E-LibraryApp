package com.project.app.hadiyankp.repository;

import com.project.app.hadiyankp.entity.library.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,String> {
    Page<Subject> findAll(Specification<Subject> specification, Pageable pageable);

}
