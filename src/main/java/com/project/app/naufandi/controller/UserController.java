package com.project.app.naufandi.controller;

import com.project.app.naufandi.dto.UserDTO;
import com.project.app.naufandi.entity.User;
import com.project.app.naufandi.response.PageResponse;
import com.project.app.naufandi.service.UserService;
import com.project.app.naufandi.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController  {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<WebResponse<User>> createUser(@RequestBody User request){
        User user = this.userService.create(request);
        WebResponse<User> webResponse = new WebResponse<>("Successfully created new user", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(webResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<WebResponse<User>> getById(@PathVariable("userId") String id){
        User user = userService.getActiveUser(id);
        WebResponse<User> response = new WebResponse<>(String.format("User with id %s found", id), user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<User>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "identitynumber", required = false) String identityNumber) {
        Pageable pageable = PageRequest.of(page, size);
        UserDTO userDTO = new UserDTO(name, identityNumber);

        Page<User> users = this.userService.listWithPage(pageable, userDTO);

        PageResponse<User> pageResponse = new PageResponse<>(
                users.getContent(),
                users.getTotalElements(),
                users.getTotalPages(),
                page,
                size
        );
        WebResponse<PageResponse<User>> response = new WebResponse<>("Successfully get data user", pageResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<WebResponse<User>> updateUserById(@RequestBody User request){
        User update = this.userService.update(request);
        WebResponse<User> webResponse = new WebResponse<>("Successfully update user", update);
        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }

    @DeleteMapping("/{userId}")
//    @RolesAllowed("ADMIN_ROLE")
    public ResponseEntity<WebResponse<String>> deleteUserById(@PathVariable("userId") String id){
        String message = this.userService.delete(id);
        WebResponse<String> webResponse = new WebResponse<>(message, id);
        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
