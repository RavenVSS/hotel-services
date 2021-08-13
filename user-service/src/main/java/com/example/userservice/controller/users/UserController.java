package com.example.userservice.controller.users;

import com.example.userservice.controller.users.dto.in.UserCreateDto;
import com.example.userservice.controller.users.dto.out.UserDto;
import com.example.userservice.model.users.UserCreateArg;
import com.example.userservice.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("registration")
    @PreAuthorize("permitAll()")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addNewUser(@RequestBody UserCreateDto userCreateDto) {
        UserCreateArg userCreateArg = userMapper.fromDto(userCreateDto);
        userService.create(userCreateArg);
    }

    @GetMapping("current")
    @PreAuthorize("hasRole('WORKER') || hasRole('USER')")
    @ResponseStatus(value = HttpStatus.OK)
    //TODO add current user
    public UserDto getCurrentUser() {
        return userMapper.toDto(userService.findAt(17));
    }

    @GetMapping("confirm")@ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("permitAll()")
    public void confirmEmail(@RequestParam("hash") String hash,
                             @RequestParam("id") Integer id) {
        userService.confirm(hash, id);
    }

    @GetMapping("{id}")@ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('WORKER')")
    public UserDto getAtUser(@PathVariable("id") Integer id) {
        return userMapper.toDto(userService.findAt(id));
    }

    @GetMapping("search/name")@ResponseStatus(value = HttpStatus.OK)
    public List<UserDto> getUserByName(@RequestParam("firstName") String firstName,
                                       @RequestParam("secondName") String secondName) {
        return userMapper.toList(userService.findByName(firstName, secondName));
    }

    @GetMapping("search/login")
    @PreAuthorize("hasRole('WORKER')")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto getUserByName(@RequestParam("login") String login) {
        return userMapper.toDto(userService.findByLogin(login));
    }
}