package org.shykhov.kotlinauthapp.controller

import org.shykhov.kotlinauthapp.entity.AuthenticationRequest
import org.shykhov.kotlinauthapp.entity.AuthenticationResponse
import org.shykhov.kotlinauthapp.entity.RegisterRequest
import org.shykhov.kotlinauthapp.service.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController (
    val authService: AuthenticationService
) {
    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<AuthenticationResponse> {
        val response = authService.register(registerRequest)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody authRequest: AuthenticationRequest ): ResponseEntity<AuthenticationResponse> {
        val response = authService.authenticate(authRequest)
        return ResponseEntity.ok(response)
    }
}