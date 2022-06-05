package com.emeritus.course.controller;

import com.emeritus.course.model.EmeritusUser;
import com.emeritus.course.service.UserService;
import com.emeritus.course.util.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(headers = HttpHeaders.AUTHORIZATION)
    @Secured({ "SYSTEM_ADMIN" })
    public Iterable<EmeritusUser> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{userName}", headers = HttpHeaders.AUTHORIZATION)
    @Secured({"SYSTEM_ADMIN"})
    public ResponseEntity<?> getUser(@Validated @PathVariable("userName") String userName){
        EmeritusUser existingEmeritusUser = userService.getUser(userName);
        return new ResponseEntity<>(existingEmeritusUser, HttpStatus.OK);
    }

    @GetMapping(path = "/role/{role}", headers = HttpHeaders.AUTHORIZATION)
    @Secured({ "SYSTEM_ADMIN" })
    public Iterable<EmeritusUser> getAllUsers(@Validated @PathVariable("role") String role){
        return userService.getAllUsers(role);
    }

    @PostMapping(headers = HttpHeaders.AUTHORIZATION)
    @Secured({ "SYSTEM_ADMIN" })
    public ResponseEntity<?> saveUser(@Validated @RequestBody EmeritusUser emeritusUser){
        EmeritusUser savedEmeritusUser = userService.saveUser(emeritusUser);
        return new ResponseEntity<>(savedEmeritusUser, HttpStatus.CREATED);
    }

    @Secured({ "SYSTEM_ADMIN" })
    @DeleteMapping(path = "/{userName}", headers = HttpHeaders.AUTHORIZATION)
    public void deleteUser(@Validated @PathVariable("userName") String userName){
        userService.deleteUser(userName);
    }

    @Secured({ "SYSTEM_ADMIN" })
    @PutMapping(path = "/{userName}", headers = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<?> updateUser(@PathVariable("userName") String userName, @Validated @RequestBody EmeritusUser emeritusUser){
        EmeritusUser updatedEmeritusUser = userService.updateUser(userName, emeritusUser);
        return new ResponseEntity<>(updatedEmeritusUser, HttpStatus.OK);
    }

}
