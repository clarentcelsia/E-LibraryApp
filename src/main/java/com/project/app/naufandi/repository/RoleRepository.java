package com.project.app.naufandi.repository;

import com.project.app.naufandi.entity.Role;
import com.project.app.naufandi.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRole(UserRole role);

    Boolean existsByRole(UserRole role);
}
