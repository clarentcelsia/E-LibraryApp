package com.project.app.naufandi.service;

import com.project.app.naufandi.dto.UserDTO;
import com.project.app.naufandi.entity.Role;
import com.project.app.naufandi.entity.User;
import com.project.app.naufandi.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    UserResponse create(User user, Set<Role> roles);

    User create(User user);

    User get(String id);

    User getActiveUser(String id);

    List<User> list();

    Page<User> listWithPage(Pageable pageable, UserDTO userDTO);

    User update(User user);

    String delete(String id);
}
