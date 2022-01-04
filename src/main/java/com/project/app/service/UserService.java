package com.project.app.service;

import com.project.app.entity.User;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.UserRepository;
import com.project.app.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    FileServiceImpl fileService;

    public User create(User user) {
        User save = userRepository.save(user);

        Customer customer = new Customer();
        customer.setName(save.getName());
        customer.setUserId(save.getUserId());
        customerService.createCustomer(customer);

        return save ;
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("user id not found"));
    }
}
