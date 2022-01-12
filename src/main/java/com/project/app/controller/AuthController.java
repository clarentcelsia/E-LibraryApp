package com.project.app.controller;

import com.project.app.entity.*;
import com.project.app.request.AdminRegisterRequest;
import com.project.app.request.LoginRequest;
import com.project.app.request.UserRegisterRequest;
import com.project.app.response.AdministratorResponse;
import com.project.app.response.LoginResponse;
import com.project.app.response.Response;
import com.project.app.response.UserResponse;
import com.project.app.security.jwt.JwtUtils;
import com.project.app.service.AdministratorService;
import com.project.app.service.RoleService;
import com.project.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private AdministratorService administratorService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/registeruser")
    public ResponseEntity<Response<?>> registeruser(
            @RequestBody UserRegisterRequest request
    ){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setIdentityNumber(request.getIdentityNumber());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setBirthDate(request.getBirthDate());
        user.setStatus(request.getStatus());

        Set<Role> roleSet = new HashSet<>();
        Set<String> roles = request.getRoles();
        for (String role : roles) {
            Role role1 = roleService.create(role);
            roleSet.add(role1);
        }

        UserResponse userResponse = userService.create(user, roleSet);
        Response<?> response = new Response<>("New User Created", userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/userlogin")
    public ResponseEntity<Response<?>> userlogin(@RequestBody LoginRequest request) {
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

        Response<?> response = new Response<>("Login success", loginResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}