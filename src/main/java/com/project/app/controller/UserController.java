package com.project.app.controller;

import com.project.app.entity.User;
import com.project.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//SAMPLE
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    public User createUser(@RequestBody User users) {
        return service.create(users);

    }
}
