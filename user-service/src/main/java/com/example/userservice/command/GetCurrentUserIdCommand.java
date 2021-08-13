package com.example.userservice.command;

import com.example.userservice.command.core.Command;
import com.example.userservice.config.CustomPrincipal;
import com.example.userservice.config.UserCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCurrentUserIdCommand implements Command<Void, Integer> {

    @Override
    public Integer execute(Void unused) {
        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUserId();
    }
}
