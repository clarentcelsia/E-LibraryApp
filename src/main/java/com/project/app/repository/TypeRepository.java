package com.project.app.repository;

import com.project.app.entity.library.Subject;
import com.project.app.entity.library.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type ,String> {
    Page<Type> findAll(Specification<Type> specification, Pageable pageable);

}
