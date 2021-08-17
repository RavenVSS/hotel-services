package com.example.reservationservice.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
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
