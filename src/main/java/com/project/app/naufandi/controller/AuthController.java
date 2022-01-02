package com.project.app.naufandi.controller;

import com.project.app.naufandi.entity.Role;
import com.project.app.naufandi.entity.User;
import com.project.app.naufandi.entity.UserDetailImpl;
import com.project.app.naufandi.request.LoginRequest;
import com.project.app.naufandi.request.RegisterRequest;
import com.project.app.naufandi.response.LoginResponse;
import com.project.app.naufandi.response.UserResponse;
import com.project.app.naufandi.security.jwt.JwtUtils;
import com.project.app.naufandi.service.RoleService;
import com.project.app.naufandi.service.UserService;
import com.project.app.naufandi.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<WebResponse<?>> register(@RequestBody RegisterRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setIdentityNumber(request.getIdentityNumber());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setBirthDate(request.getBirthDate());
        user.setPhoto(request.getPhoto());
        user.setStatus(request.getStatus());

        Set<Role> roleSet = new HashSet<>();
        Set<String> roles = request.getRoles();
        for (String role : roles) {
            Role role1 = roleService.create(role);
            roleSet.add(role1);
        }

        UserResponse userResponse = userService.create(user, roleSet);
        WebResponse<?> response = new WebResponse<>("Successfully created new user", userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<WebResponse<?>> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        Set<String> roles = new HashSet<>();
        for (GrantedAuthority authority : userDetail.getAuthorities()){
            roles.add(authority.getAuthority());
        }
        LoginResponse loginResponse = new LoginResponse(jwt, roles);

        WebResponse<?> response = new WebResponse<>("Login success", loginResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}