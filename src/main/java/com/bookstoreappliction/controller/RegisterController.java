package com.bookstoreappliction.controller;

import com.bookstoreappliction.config.TokenUtility;
import com.bookstoreappliction.dto.JwtResponse;
import com.bookstoreappliction.dto.LoginReq;
import com.bookstoreappliction.dto.UserDTO;
import com.bookstoreappliction.model.User;
import com.bookstoreappliction.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RegisterController {

    @Autowired
    public UserService userService;

    @Autowired
    public TokenUtility tokenUtility;

    @GetMapping("/test")
    public String test(){
        return "pushpak!";
    }
    @GetMapping("/testing")
    public String test2(){
        return "changes updated";
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userReqDTO){
        boolean flag=userService.checkEmail(userReqDTO);
        if(flag)
            return ResponseEntity.ok(userService.addUser(userReqDTO));
        else
            return ResponseEntity.ok("Email already present!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginReq loginReq) {
        Optional<User> login = userService.userLogin(loginReq);
        if (login.isPresent()) {
            return ResponseEntity.ok(new JwtResponse(tokenUtility.createToken(login.get().getId(), login.get().getRole())));
        } else {
            return new ResponseEntity<>("User login not successfully", HttpStatus.ACCEPTED);
        }
    }
}
