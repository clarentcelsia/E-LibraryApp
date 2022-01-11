package com.project.app.service.impl;

import com.project.app.dto.UserDTO;
import com.project.app.entity.Role;
import com.project.app.entity.User;
import com.project.app.entity.UserDetailImpl;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.response.UserResponse;
import com.project.app.service.RoleService;
import com.project.app.service.UserService;
import com.project.app.repository.UserRepository;
import com.project.app.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
                save.getUsername(),
                save.getPassword(),
                save.getStatus(),
                save.getCreatedAt(),
                save.getUpdatedAt(),
                strRoles
        );
    }

//    @Override
//    public User create(User user) {
//        return (User) this.userRepository.save(user);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return UserDetailImpl.build(user);
    }

    public UserServiceImpl() {
    }

    @Override
    public User getById(String id) {
        return this.findByIdOrThrowNotFound(id);
    }

    @Override
    public User getActiveUser(String id) {
        return userRepository.getActiveUser(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<User> list() {
        return this.userRepository.findAll();
    }

    @Override
    public Page<User> listWithPage(Pageable pageable, UserDTO userDTO) {
        Specification<User> specification = UserSpecification.getSpecification(userDTO);
        return userRepository.findAll(specification, pageable);
    }

//    @Override
//    public User update(User user) {
//        findByIdOrThrowNotFound(user.getId());
//        return create(user);
//    }

    @Override
    public String delete(String id) {
        User user = this.findByIdOrThrowNotFound(id);
        if (user.getDeleted()){
            throw new ResourceNotFoundException("User not found");
        }
        user.setDeleted(true);
        userRepository.save(user);
        return "User deleted";
    }

    private User findByIdOrThrowNotFound(String id){
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()){
            return user.get();
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }
}
