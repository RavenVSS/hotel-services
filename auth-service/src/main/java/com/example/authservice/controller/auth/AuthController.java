package com.example.authservice.controller.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("auth")
class AuthController {

    @GetMapping("user")
    public Principal getPrincipal(Principal principal) {
        return principal;
    }
}
