package org.shykhov.kotlinauthapp.controller

import org.shykhov.kotlinauthapp.entity.AuthenticationResponse
import org.shykhov.kotlinauthapp.entity.RegisterRequest
import org.shykhov.kotlinauthapp.service.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.rmi.registry.Registry

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController (
    val authService: AuthenticationService
) {
    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authService.register(registerRequest))
    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody registerRequest: RegisterRequest): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authService.authenticate(registerRequest))
    }
}