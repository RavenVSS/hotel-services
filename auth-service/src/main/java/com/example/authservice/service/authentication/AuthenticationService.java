package com.example.authservice.service.authentication;

import com.example.authservice.model.users.UserTypes;

public interface AuthenticationService {

    Integer getCurrentUserId();

    UserTypes getCurrentRole();
}
