package org.shykhov.kotlinauthapp.service

import org.shykhov.kotlinauthapp.entity.AuthenticationResponse
import org.shykhov.kotlinauthapp.entity.RegisterRequest
import org.shykhov.kotlinauthapp.entity.Role
import org.shykhov.kotlinauthapp.entity.User
import org.shykhov.kotlinauthapp.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService (
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val jwtService: JwtService
){
    fun register(registerRequest: RegisterRequest): AuthenticationResponse {
        val user = User(0,
            registerRequest.firstname,
            registerRequest.lastname,
            registerRequest.email,
            passwordEncoder.encode(registerRequest.password),
            Role.USER)
        userRepository.save(user)
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken)
    }

    fun authenticate(registerRequest: RegisterRequest): AuthenticationResponse {
        TODO("Not yet implemented")
    }
}