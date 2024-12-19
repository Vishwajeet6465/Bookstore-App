package com.bookstoreappliction.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    //@Pattern(regexp = "")
    private String fname;
    private String lname;
    private LocalDate dob;
    private String password;
    private String emailId;
    private String role;
}
