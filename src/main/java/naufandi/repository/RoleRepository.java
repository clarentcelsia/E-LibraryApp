package naufandi.repository;

import naufandi.entity.Role;
import naufandi.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRole(UserRole role);

    Boolean existByRole(UserRole role);
}
