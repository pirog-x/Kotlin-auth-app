package org.shykhov.kotlinauthapp.service

import org.shykhov.kotlinauthapp.entity.*
import org.shykhov.kotlinauthapp.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService (
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val jwtService: JwtService,
    val authenticationManager: AuthenticationManager,
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

    fun authenticate(authRequest: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
            authRequest.email,
            authRequest.password
        ))
        val user = userRepository.findByEmail(authRequest.email).orElseThrow()
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken)
    }
}