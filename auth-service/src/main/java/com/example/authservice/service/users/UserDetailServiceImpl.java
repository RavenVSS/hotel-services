package com.example.authservice.service.users;

import com.example.authservice.config.security.UserCustom;
import com.example.authservice.exceptions.EntityNotFoundException;
import com.example.authservice.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        com.example.authservice.model.users.User user = userRepository.findOptionalByLogin(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return new UserCustom(user.getLogin(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getType().name()),
                user.getId(),
                user.getType());
    }
}
