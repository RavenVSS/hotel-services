package com.example.userservice.service.profiles;

import com.example.userservice.model.profiles.Profile;
import com.example.userservice.model.profiles.ProfileCreateArg;

import java.util.List;

public interface ProfileService {

    void create(ProfileCreateArg profileCreateArg);

    void delete(Integer id);

    void update(ProfileCreateArg profileCreateArg, Integer id);

    List<Profile> findAll();

    Profile findAt(Integer id);

    Profile findByUserId(Integer userId);
}
