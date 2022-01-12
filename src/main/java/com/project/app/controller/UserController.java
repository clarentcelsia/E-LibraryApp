package com.project.app.controller;

import com.project.app.dto.UserDTO;
import com.project.app.entity.User;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.UserService;
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

    @GetMapping("/{userId}")
    public ResponseEntity<Response<User>> getById(@PathVariable("userId") String id){
        User user = userService.getActiveUser(id);
        Response<User> response = new Response<>(String.format("User with id %s found", id), user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<User>>> listWithPage(
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
        Response<PageResponse<User>> response = new Response<>("Successfully get data user", pageResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @PutMapping
//    public ResponseEntity<Response<User>> updateUserById(@RequestBody User request){
//        User update = this.userService.update(request);
//        Response<User> webResponse = new Response<>("Successfully update user", update);
//        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
//    }

    @DeleteMapping("/{userId}")
//    @RolesAllowed("ADMIN_ROLE")
    public ResponseEntity<Response<String>> deleteUserById(@PathVariable("userId") String id){
        String message = this.userService.delete(id);
        Response<String> webResponse = new Response<>(message, id);
        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
