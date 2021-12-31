package naufandi.service.impl;

import naufandi.entity.Role;
import naufandi.exception.NotFoundException;
import naufandi.repository.RoleRepository;
import naufandi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import static naufandi.entity.UserRole.ADMIN_ROLE;
import static naufandi.entity.UserRole.USER_ROLE;

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role create(String strRole) {
        if (strRole == null) {
            if (!roleRepository.existByRole(USER_ROLE)) {
                Role userRole = new Role(USER_ROLE);
                return roleRepository.save(userRole);
            }
            return roleRepository.findByRole(USER_ROLE)
                    .orElseThrow(() -> new NotFoundException("Role Not Found"));
        } else {
            if (strRole.equalsIgnoreCase("admin")) {
                if (!roleRepository.existByRole(ADMIN_ROLE)) {
                    Role adminRole = new Role(ADMIN_ROLE);
                    return roleRepository.save(adminRole);
                }
                return roleRepository.findByRole(ADMIN_ROLE)
                        .orElseThrow(() -> new NotFoundException("Role Not Found"));
            } else {
                if (!roleRepository.existByRole(USER_ROLE)) {
                    Role userRole = new Role(USER_ROLE);
                    return roleRepository.save(userRole);
                }
                return roleRepository.findByRole(USER_ROLE)
                        .orElseThrow(() -> new NotFoundException("Role Not Found"));
            }
        }
    }
}
