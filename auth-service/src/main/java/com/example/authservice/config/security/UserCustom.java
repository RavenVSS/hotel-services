package com.example.authservice.config.security;

import com.example.authservice.model.users.UserTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserCustom extends User {
    private Integer userId;
    private UserTypes type;

    public UserCustom(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer userId, UserTypes type) {
        super(username, password, authorities);
        this.userId = userId;
        this.type = type;
    }

    public UserCustom(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Integer userId, UserTypes type) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserTypes getType() {
        return type;
    }

    public void setType(UserTypes type) {
        this.type = type;
    }
}
