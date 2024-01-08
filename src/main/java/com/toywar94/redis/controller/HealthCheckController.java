package com.toywar94.redis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @RequestMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }
}
