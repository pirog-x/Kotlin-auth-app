package org.shykhov.kotlinauthapp.controller


import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.shykhov.kotlinauthapp.entity.AuthenticationRequest
import org.shykhov.kotlinauthapp.entity.AuthenticationResponse
import org.shykhov.kotlinauthapp.entity.RegisterRequest
import org.shykhov.kotlinauthapp.service.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import kotlin.jvm.Throws

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController (
    val authService: AuthenticationService
) {
    @PostMapping("/register")
    fun register(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<AuthenticationResponse> {
        val response = authService.register(registerRequest)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @PostMapping("/authenticate")
    fun authenticate(@Valid @RequestBody authRequest: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        val response = authService.authenticate(authRequest)
        return ResponseEntity.ok(response)
    }

    @Throws(IOException::class)
    @PostMapping("/refresh-token")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        return authService.refreshToken(request, response)
    }
}