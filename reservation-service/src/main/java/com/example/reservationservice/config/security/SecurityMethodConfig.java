package com.example.reservationservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityMethodConfig extends GlobalMethodSecurityConfiguration {

    @Bean
    public OAuth2MethodSecurityExpressionHandler oauthExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }
}
