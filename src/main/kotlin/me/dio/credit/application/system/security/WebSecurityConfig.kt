package me.dio.credit.application.system.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor


@Configuration
class WebSecurityConfig {

    @Bean
    fun userDetailsService(): InMemoryUserDetailsManager {
        val user = User.withDefaultPasswordEncoder()
            .username("demo")
            .password("demo")
            .roles("managers")
            .build()

        return InMemoryUserDetailsManager(user)
    }
}