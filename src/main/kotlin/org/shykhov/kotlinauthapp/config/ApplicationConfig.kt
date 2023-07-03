package org.shykhov.kotlinauthapp.config

import lombok.RequiredArgsConstructor
import org.shykhov.kotlinauthapp.dao.entity.User
import org.shykhov.kotlinauthapp.dao.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Configuration
@RequiredArgsConstructor
class ApplicationConfig(
    private val userRepository: UserRepository
) {

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username -> userRepository.findByEmail(username)
                .orElseThrow { UsernameNotFoundException("User not found") }
        }
    }
}