package com.example.userservice.model.profiles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "profile")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "address")
    private String address;

    @Column(name = "passport")
    private Integer passport;

    @Column(name = "passport_date")
    private Date passportDate;

    @Column(name = "passport_region")
    private String passportRegion;

    @Column(name = "birthday_date")
    private Date birthdayDate;

    @Column(name = "town_name")
    private String townName;
}
