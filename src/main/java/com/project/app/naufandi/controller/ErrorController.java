package com.project.app.naufandi.controller;

import com.project.app.naufandi.exception.NotFoundException;
import com.project.app.naufandi.util.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.NotYetBoundException;

@RestController
public class ErrorController {
    public ErrorController(){
    }

    @ExceptionHandler({NotYetBoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        WebResponse<String> response = new WebResponse<>(exception.getMessage(), null);
        return new ResponseEntity<>(response, status);
    }
}
