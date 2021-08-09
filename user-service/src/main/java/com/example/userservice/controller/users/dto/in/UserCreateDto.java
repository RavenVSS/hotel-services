package com.example.userservice.controller.users.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String login;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String secondName;
    private String middleName;
}
