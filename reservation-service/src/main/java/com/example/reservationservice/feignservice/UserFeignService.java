package com.example.reservationservice.feignservice;

import com.example.reservationservice.model.users.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service")
public interface UserFeignService {

    @GetMapping("/users/current")
    public User getCurrentUser();
}
