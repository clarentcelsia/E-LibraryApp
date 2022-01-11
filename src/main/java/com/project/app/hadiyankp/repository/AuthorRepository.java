package com.project.app.hadiyankp.repository;

import com.project.app.hadiyankp.entity.library.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface AuthorRepository extends JpaRepository<Author,String> {
    Page<Author> findAll(Specification<Author> specification, Pageable pageable);

}
