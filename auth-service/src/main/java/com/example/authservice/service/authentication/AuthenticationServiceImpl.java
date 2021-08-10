package com.example.authservice.service.authentication;

import com.example.authservice.config.security.UserCustom;
import com.example.authservice.model.users.UserTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public Integer getCurrentUserId() {
        UserCustom userCustom = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userCustom.getUserId();
    }

    @Override
    public UserTypes getCurrentRole() {
        UserCustom userCustom = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userCustom.getType();
    }
}
