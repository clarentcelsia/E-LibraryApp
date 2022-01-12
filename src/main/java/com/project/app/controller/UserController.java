package com.project.app.controller;

import com.project.app.entity.User;
import com.project.app.response.WebResponse;
import com.project.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    public ResponseEntity<WebResponse<User>> create(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new WebResponse<>("Success", service.create(user)));
    }
}
