package com.project.app.naufandi.service.impl;

import com.project.app.naufandi.entity.Role;
import com.project.app.naufandi.entity.UserRole;
import com.project.app.naufandi.exception.NotFoundException;
import com.project.app.naufandi.service.RoleService;
import com.project.app.naufandi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role create(String strRole) {
        if (strRole == null) {
            if (!roleRepository.existsByRole(UserRole.USER_ROLE)) {
                Role userRole = new Role(UserRole.USER_ROLE);
                return roleRepository.save(userRole);
            }
            return roleRepository.findByRole(UserRole.USER_ROLE)
                    .orElseThrow(() -> new NotFoundException("Role Not Found"));
        } else {
            if (strRole.equalsIgnoreCase("admin")) {
                if (!roleRepository.existsByRole(UserRole.ADMIN_ROLE)) {
                    Role adminRole = new Role(UserRole.ADMIN_ROLE);
                    return roleRepository.save(adminRole);
                }
                return roleRepository.findByRole(UserRole.ADMIN_ROLE)
                        .orElseThrow(() -> new NotFoundException("Role Not Found"));
            } else {
                if (!roleRepository.existsByRole(UserRole.USER_ROLE)) {
                    Role userRole = new Role(UserRole.USER_ROLE);
                    return roleRepository.save(userRole);
                }
                return roleRepository.findByRole(UserRole.USER_ROLE)
                        .orElseThrow(() -> new NotFoundException("Role Not Found"));
            }
        }
    }
}
