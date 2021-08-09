package com.example.userservice.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "salt")
    private String salt;

    @Column(name = "cookie")
    private String cookie;

    @Column(name = "reg_date")
    private Date regDate;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserTypes type;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "middle_name")
    private String middleName;

    @PrePersist
    public void setDataReg() {
        regDate = new Date();
    }
}
