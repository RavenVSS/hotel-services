package com.example.userservice.controller.profiles;

import com.example.userservice.controller.profiles.dto.in.ProfileCreateDto;
import com.example.userservice.controller.profiles.dto.out.ProfileDto;
import com.example.userservice.model.profiles.Profile;
import com.example.userservice.model.profiles.ProfileCreateArg;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(source = "id", target = "profileId")
    ProfileDto toDto(Profile profile);

    @Mapping(source = "id", target = "profileId")
    List<ProfileDto> toList(List<Profile> profileList);

    ProfileCreateArg fromDto(ProfileCreateDto profileCreateDto);
}
