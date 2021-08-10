package com.example.userservice.service.profiles;

import com.example.userservice.exceptions.EntityNotFoundException;
import com.example.userservice.model.profiles.Profile;
import com.example.userservice.model.profiles.ProfileCreateArg;
import com.example.userservice.repository.profiles.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    @Transactional
    public void create(ProfileCreateArg profileCreateArg) {
        profileRepository.save(Profile.builder()
            .address(profileCreateArg.getAddress())
            .userId(profileCreateArg.getUserId())
            .birthdayDate(profileCreateArg.getBirthdayDate())
            .passport(profileCreateArg.getPassport())
            .passportDate(profileCreateArg.getPassportDate())
            .passportRegion(profileCreateArg.getPassportRegion())
            .townName(profileCreateArg.getTownName())
            .build());
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        profileRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(ProfileCreateArg profileCreateArg, Integer id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));

        profile.setAddress(profileCreateArg.getAddress());
        profile.setUserId(profileCreateArg.getUserId());
        profile.setBirthdayDate(profileCreateArg.getBirthdayDate());
        profile.setPassport(profileCreateArg.getPassport());
        profile.setPassportDate(profileCreateArg.getPassportDate());
        profile.setPassportRegion(profileCreateArg.getPassportRegion());
        profile.setTownName(profileCreateArg.getTownName());

        profileRepository.save(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Profile> findAll() {
        List<Profile> profileList = profileRepository.findAll();
        if (profileList.isEmpty()) throw new EntityNotFoundException("Profile not found");
        return profileList;
    }

    @Override
    @Transactional(readOnly = true)
    public Profile findAt(Integer id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Profile findByUserId(Integer userId) {
        Profile profile = profileRepository.findByUserId(userId);
        if (profile == null) throw new EntityNotFoundException("Profile not found");
        return profile;
    }
}
