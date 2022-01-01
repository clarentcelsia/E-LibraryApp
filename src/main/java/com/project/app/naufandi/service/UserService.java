package com.project.app.naufandi.service;

import com.project.app.naufandi.entity.Role;
import com.project.app.naufandi.entity.User;
import com.project.app.naufandi.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService {

    UserResponse create(User user, Set<Role> roles);
}
