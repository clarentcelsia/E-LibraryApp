package com.project.app.naufandi.repository;

import com.project.app.naufandi.entity.Administrator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, String> {
    Optional<Administrator> findByUsername(String username);

    Page<Administrator> findAll(Specification<Administrator> specification, Pageable pageable);

    @Query(value = "SELECT a FROM User a WHERE a.isDeleted = false AND a.id = ?1")
    Optional<Administrator> getActiveAdministrator(String id);
}
