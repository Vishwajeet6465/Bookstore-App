package com.bookstoreappliction.service;

import com.bookstoreappliction.dto.LoginReq;
import com.bookstoreappliction.dto.UserDTO;
import com.bookstoreappliction.exception.IdNotFoundException;
import com.bookstoreappliction.model.User;
import com.bookstoreappliction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDTO addUser(UserDTO userReqDTO) {
        User user=new User(userReqDTO);
        user.setRegisteredDate(LocalDate.now());
        user.setUpdatedDate(LocalDate.now());
        return mapToDTO(userRepository.save(user));
    }

    private UserDTO mapToDTO(User user) {
        UserDTO userDTO=new UserDTO();
        userDTO.setDob(user.getDob());
        userDTO.setRole(user.getRole());
        userDTO.setEmailId(user.getEmailId());
        userDTO.setFname(user.getFname());
        userDTO.setLname(user.getLname());
        userDTO.setEmailId(user.getEmailId());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    @Override
    public UserDTO getUser(long id) {
        return mapToDTO(userRepository.findById(id).orElseThrow(()->new IdNotFoundException("ID not found")));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public void deleteUser(long id) {
        userRepository.findById(id).orElseThrow(()->new IdNotFoundException("ID not found"));
        userRepository.deleteById(id);

    }

    @Override
    public UserDTO updateUser(long id, UserDTO userReqDTO) {
        User user=userRepository.findById(id).orElseThrow(()->new IdNotFoundException("Impossible to get here."));
        user.setUpdatedDate(LocalDate.now());
        user.setDob(userReqDTO.getDob());
        user.setRole(userReqDTO.getRole());
        user.setEmailId(userReqDTO.getEmailId());
        user.setFname(userReqDTO.getFname());
        user.setLname(userReqDTO.getLname());
        user.setPassword(userReqDTO.getPassword());
        return mapToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO adminGetUser(long id) {
        return mapToDTO(userRepository.findById(id).orElseThrow(()->new IdNotFoundException("ID not found")));
    }

    @Override
    public Optional<User> userLogin(LoginReq loginReq) {
        Optional<User> userLogin = userRepository.findByEmailIdAndPassword(loginReq.getEmailId(), loginReq.getPassword());

        if (userLogin.isPresent()) {
            return userLogin;
        } else {
            return null;
        }
    }

    @Override
    public User getUserbyId(long id) {
        return userRepository.findById(id).orElseThrow(()->new IdNotFoundException("ID Not Found"));
    }

    @Override
    public boolean checkEmail(UserDTO userReqDTO) {
        List<User> users=userRepository.findAll().stream().filter(user -> Objects.equals(user.getEmailId(), userReqDTO.getEmailId())).toList();
        return users.isEmpty();
    }
}
