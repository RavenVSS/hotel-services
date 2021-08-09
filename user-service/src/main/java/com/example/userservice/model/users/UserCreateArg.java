package com.example.userservice.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateArg {
    private String login;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String secondName;
    private String middleName;
}
