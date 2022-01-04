package com.project.app.controller;

import com.project.app.entity.User;
import com.project.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public User create(
            @RequestBody User user
    ){
        User save = userService.create(user);
        return save;

    }
}
