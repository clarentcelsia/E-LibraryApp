package com.project.app.hadiyankp.repository;

import com.project.app.hadiyankp.entity.library.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books,String> {

    Page<Books> findAll(Specification<Books> specification, Pageable pageable);
}
