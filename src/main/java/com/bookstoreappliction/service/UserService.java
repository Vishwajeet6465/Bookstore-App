package com.bookstoreappliction.service;

import com.bookstoreappliction.dto.LoginReq;
import com.bookstoreappliction.dto.UserDTO;
import com.bookstoreappliction.model.User;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO addUser(@Valid UserDTO userReqDTO);

    UserDTO getUser(long id);

    List<UserDTO> getAllUsers();

    void deleteUser(long id);

    UserDTO updateUser(long id, UserDTO userReqDTO);

    UserDTO adminGetUser(long id);

    Optional<User> userLogin(LoginReq loginReq);

    User getUserbyId(long id);

    boolean checkEmail(@Valid UserDTO userReqDTO);
}
