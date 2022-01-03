package com.project.app.service;

import com.project.app.entity.User;
import com.project.app.files.Files;
import com.project.app.repository.UserRepository;
import com.project.app.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileServiceImpl fileService;

    public User create(User user) {
        return userRepository.save(user);
    }
}
