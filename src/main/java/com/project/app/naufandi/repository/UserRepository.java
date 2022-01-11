package com.project.app.naufandi.repository;

import com.project.app.naufandi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    Page<User> findAll(Specification<User> specification, Pageable pageable);

    @Query(value = "SELECT u FROM User u WHERE u.isDeleted = false AND u.id = ?1")
    Optional<User> getActiveUser(String id);
}
