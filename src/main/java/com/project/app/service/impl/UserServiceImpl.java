package com.project.app.service.impl;

import com.project.app.entity.User;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.UserRepository;
import com.project.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    FileServiceImpl fileService;

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    public User getUserById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("user id not found"));
    }
}
