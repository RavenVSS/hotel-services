package com.example.userservice.controller.users;

import com.example.userservice.controller.users.dto.in.UserCreateDto;
import com.example.userservice.controller.users.dto.out.UserDto;
import com.example.userservice.model.users.User;
import com.example.userservice.model.users.UserCreateArg;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    UserDto toDto(User user);

    @Mapping(source = "id", target = "userId")
    List<UserDto> toList(List<User> users);

    UserCreateArg fromDto(UserCreateDto dto);
}
