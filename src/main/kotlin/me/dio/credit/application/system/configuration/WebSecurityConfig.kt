package me.dio.credit.application.system.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager


@Configuration
class WebSecurityConfig {

    @Bean
    fun userDetailsService(): InMemoryUserDetailsManager {
        val user = User.withDefaultPasswordEncoder()
            .username("demo")
            .password("demo")
            .roles("USER")
            .build()

        return InMemoryUserDetailsManager(user)
    }
}