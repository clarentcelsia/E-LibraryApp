package com.project.app.naufandi.repository;

import com.project.app.naufandi.entity.Role;
import com.project.app.naufandi.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRole(UserRole role);

    Boolean existByRole(UserRole role);
}
