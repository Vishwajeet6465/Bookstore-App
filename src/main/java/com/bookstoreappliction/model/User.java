package com.bookstoreappliction.model;

import com.bookstoreappliction.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "User_Data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String fname;
    private String lname;
    private LocalDate dob;
    private LocalDate registeredDate;
    private LocalDate updatedDate;
    private String password;
    private String emailId;
    private String role;

    public User(UserDTO userDTO){
        this.dob=userDTO.getDob();
        this.emailId=userDTO.getEmailId();
        this.fname= userDTO.getFname();
        this.lname= userDTO.getLname();
        this.password=userDTO.getPassword();
        this.role=userDTO.getRole();
    }
}
