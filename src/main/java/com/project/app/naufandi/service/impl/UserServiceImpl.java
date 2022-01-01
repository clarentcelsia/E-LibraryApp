package com.project.app.naufandi.service.impl;

import com.project.app.naufandi.entity.Role;
import com.project.app.naufandi.entity.User;
import com.project.app.naufandi.entity.UserDetailImpl;
import com.project.app.naufandi.response.UserResponse;
import com.project.app.naufandi.service.RoleService;
import com.project.app.naufandi.service.UserService;
import com.project.app.naufandi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public UserResponse create(User user, Set<Role> roles) {
        user.setRoles(roles);
        User save = userRepository.save(user);

        Set<String> strRoles = new HashSet<>();
        for (Role role : save.getRoles()) {
            strRoles.add(role.getRole().name());
        }

        return new UserResponse(
                save.getId(),
                save.getIdentityNumber(),
                save.getName(),
                save.getAddress(),
                save.getEmail(),
                save.getPhoneNumber(),
                save.getBirthDate(),
                save.getPhoto(),
                save.getUsername(),
                save.getPassword(),
                save.getStatus(),
                save.getCreatedAt(),
                save.getUpdatedAt(),
                strRoles
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
        return UserDetailImpl.build(user);
    }
}
