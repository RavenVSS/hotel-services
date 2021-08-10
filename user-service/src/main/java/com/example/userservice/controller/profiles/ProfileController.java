package com.example.userservice.controller.profiles;

import com.example.userservice.controller.profiles.dto.in.ProfileCreateDto;
import com.example.userservice.controller.profiles.dto.out.ProfileDto;
import com.example.userservice.service.profiles.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @PostMapping("create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addNewProfile(@RequestBody ProfileCreateDto profileCreateDto){
        profileService.create(profileMapper.fromDto(profileCreateDto));
    }

    @PostMapping("{id}/update")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateProfile(@RequestBody ProfileCreateDto profileCreateDto,
                              @PathVariable("id") Integer id){
        profileService.update(profileMapper.fromDto(profileCreateDto), id);
    }

    @PostMapping("{id}/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProfile(@PathVariable("id") Integer id){
        profileService.delete(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProfileDto getAtProfile(@PathVariable("id") Integer id){
        return profileMapper.toDto(profileService.findAt(id));
    }

    @GetMapping("search/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProfileDto getProfileByUserId(@PathVariable("userId") Integer userId){
        return profileMapper.toDto(profileService.findByUserId(userId));
    }

    @GetMapping("current")
    @ResponseStatus(value = HttpStatus.OK)
    public ProfileDto getCurrentProfile(){
        //TODO add current user
        return profileMapper.toDto(profileService.findByUserId(17));
    }
}
