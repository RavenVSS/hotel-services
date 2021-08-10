package com.example.userservice.repository.profiles;

import com.example.userservice.model.profiles.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Profile findByUserId(Integer userId);
}
