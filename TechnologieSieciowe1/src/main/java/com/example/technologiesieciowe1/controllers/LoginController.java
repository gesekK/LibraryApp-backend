package com.example.technologiesieciowe1.controllers;


import com.example.technologiesieciowe1.entities.LoginForm;
import com.example.technologiesieciowe1.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        String token = loginService.userLogin(loginForm);
        if (token == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong login or password");
        } else {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
    }
}
