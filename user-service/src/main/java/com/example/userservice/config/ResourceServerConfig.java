package com.example.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final ResourceServerProperties sso;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(myUserInfoTokenServices());
    }

    @Bean
    public PrincipalExtractor customPrincipalExtractor() {
        return new CustomPrincipalExtractor();
    }

    @Bean
    public ResourceServerTokenServices myUserInfoTokenServices() {
        return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
    }
}
