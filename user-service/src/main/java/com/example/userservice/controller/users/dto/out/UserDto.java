package com.example.userservice.controller.users.dto.out;

import com.example.userservice.model.users.UserTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer userId;
    private String login;
    private String email;
    private String phone;
    private Date regDate;
    private UserTypes type;
    private String firstName;
    private String secondName;
    private String middleName;
}
