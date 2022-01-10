package com.project.app.service;

import com.project.app.entity.User;

public interface UserService {

    User create(User user);

    User getUserById(String id);
}
