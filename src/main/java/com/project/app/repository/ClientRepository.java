package com.project.app.repository;

import com.project.app.entity.Clients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Clients, String> {

    Page<Clients> findAll(Specification<Clients> specification, Pageable pageable);
}
