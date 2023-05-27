package me.dio.credit.application.system.security

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WelcomeController {

    @GetMapping("/api")
    fun welcome(): String {
        return "Welcome to my Spring Boot Web API"
    }
}