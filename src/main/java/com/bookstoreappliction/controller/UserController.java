package com.bookstoreappliction.controller;

import com.bookstoreappliction.config.TokenUtility;
import com.bookstoreappliction.dto.LoginReq;
import com.bookstoreappliction.dto.UserDTO;
import com.bookstoreappliction.dto.JwtResponse;
import com.bookstoreappliction.model.User;
import com.bookstoreappliction.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public TokenUtility tokenUtility;


    @GetMapping("/details")
    public ResponseEntity<String> getUser(@RequestAttribute("role") String role, @RequestAttribute("id") long id){
        if("User".equals(role)) {
            UserDTO userDTO = userService.getUser(id);
            return ResponseEntity.ok("Your details are: \n EmailID:"+userDTO.getEmailId()+"\n First Name:"+userDTO.getFname()+"\n Last Name:"+userDTO.getLname()+"\n Date of Birth:"+userDTO.getDob()+"\n Password:"+userDTO.getPassword());
        }
        else{
            return ResponseEntity.status(403).body("Access Denied");
        }
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUser(@RequestAttribute("role") String role){
        if("Admin".equals(role))
            return userService.getAllUsers();
        else
            return null;
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> AdminGetUser(@RequestAttribute("role") String role, @PathVariable long id){
        if("Admin".equals(role)) {
            UserDTO userDTO = userService.getUser(id);
            return ResponseEntity.ok("The details are: \n EmailID:"+userDTO.getEmailId()+"\n First Name:"+userDTO.getFname()+"\n Last Name:"+userDTO.getLname()+"\n Date of Birth:"+userDTO.getDob()+"\n Password:"+userDTO.getPassword());
        }
        else{
            return ResponseEntity.status(403).body("Access Denied");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delUser(@RequestAttribute("role") String role,@PathVariable long id){
        if("Admin".equals(role)) {
            userService.deleteUser(id);
            return ResponseEntity.ok("Deleted User");
        }
        else {
            return ResponseEntity.status(403).body("Access Denied");
        }
    }

    @PutMapping("/update")
    public UserDTO updateUser(@RequestAttribute("id") long id,@RequestBody UserDTO userReqDTO){
        return userService.updateUser(id,userReqDTO);
    }
}
