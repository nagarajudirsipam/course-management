package com.emeritus.course.controller;

import com.emeritus.course.model.EmeritusUser;
import com.emeritus.course.model.login.AccessRequest;
import com.emeritus.course.model.login.AccessResponse;
import com.emeritus.course.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody @Validated AccessRequest accessRequest) {
        AccessResponse accessResponse = loginService.getAccessToken(accessRequest.getUserName());
        if(accessResponse == null){
            return new ResponseEntity<>("User not exists", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accessResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody @Validated EmeritusUser emeritusUser) {
        EmeritusUser savedEmeritusUser = loginService.register(emeritusUser);
        return new ResponseEntity<>(savedEmeritusUser, HttpStatus.CREATED);
    }
}
