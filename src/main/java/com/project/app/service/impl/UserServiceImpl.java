package com.project.app.service.impl;

import com.project.app.entity.Users;
import com.project.app.repository.UserRepository;
import com.project.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public Users create(Users users) {
        return repository.save(users);
    }
}