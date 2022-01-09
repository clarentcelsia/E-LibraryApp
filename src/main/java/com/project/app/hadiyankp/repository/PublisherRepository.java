package com.project.app.hadiyankp.repository;

import com.project.app.hadiyankp.entity.library.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,String > {
    Page<Publisher> findAll(Specification<Publisher> specification, Pageable pageable);
}
