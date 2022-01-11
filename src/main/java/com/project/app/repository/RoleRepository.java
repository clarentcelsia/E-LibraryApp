package com.project.app.repository;

import com.project.app.entity.Role;
import com.project.app.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRole(UserRole role);

    Boolean existsByRole(UserRole role);
}
