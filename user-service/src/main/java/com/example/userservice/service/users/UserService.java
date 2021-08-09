package com.example.userservice.service.users;

import com.example.userservice.model.users.User;
import com.example.userservice.model.users.UserCreateArg;

import java.util.List;

public interface UserService {

    User create(UserCreateArg arg);

    void update(UserCreateArg arg, Integer userId);

    void delete(Integer userId);

    List<User> findAll();

    User findAt(Integer UserId);

    User findByLogin(String login);

    List<User> findByName(String firstName, String secondName);

    void confirm(String hash, Integer userId);
}
