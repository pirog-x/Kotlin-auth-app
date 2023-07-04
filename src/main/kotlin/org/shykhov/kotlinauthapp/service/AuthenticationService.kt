package org.shykhov.kotlinauthapp.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.io.IOException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.shykhov.kotlinauthapp.entity.*
import org.shykhov.kotlinauthapp.repository.TokenRepository
import org.shykhov.kotlinauthapp.repository.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.function.Consumer


@Service
class AuthenticationService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val jwtService: JwtService,
    val authenticationManager: AuthenticationManager,
    val tokenRepository: TokenRepository,
    val loginService: LoginService,
) {
    fun register(registerRequest: RegisterRequest): AuthenticationResponse {
        val user = User(
            id = 0,
            firstName = registerRequest.firstname,
            lastName = registerRequest.lastname,
            email = registerRequest.email,
            passwd = passwordEncoder.encode(registerRequest.password),
            role = Role.USER,
            tokens = null
        )
        val savedUser = userRepository.save(user)
        val jwtToken = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)

        saveUserToken(savedUser, jwtToken)
        loginService.save(savedUser)
        return AuthenticationResponse(jwtToken, refreshToken, logins = loginService.getLast5Logins(savedUser.id))
    }

    fun authenticate(authRequest: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.email,
                authRequest.password
            )
        )
        val user = userRepository.findByEmail(authRequest.email).orElseThrow()
        val jwtToken = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)
        revokeAllUserTokens(user)
        saveUserToken(user, jwtToken)
        loginService.save(user)
        return AuthenticationResponse(jwtToken, refreshToken, logins = loginService.getLast5Logins(user.id))
    }

    private fun saveUserToken(user: User, jwtToken: String) {
        val token = Token(
            id = 0,
            user = user,
            token = jwtToken,
            tokenType = TokenType.BEARER,
            expired = false,
            revoked = false
        )
        tokenRepository.save(token)
    }

    private fun revokeAllUserTokens(user: User) {
        val validUserTokens = tokenRepository.findAllValidTokenByUser(user.id)
        if (validUserTokens.isEmpty()) return
        validUserTokens.forEach(Consumer { token: Token ->
            token.expired = true
            token.revoked = true
        })
        tokenRepository.saveAll(validUserTokens)
    }

    @Throws(IOException::class)
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        val authHeader: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return
        }
        val refreshToken: String = authHeader.substring(7)
        val userEmail: String? = jwtService.extractUsername(refreshToken)
        if (userEmail != null) {
            val user = userRepository.findByEmail(userEmail).orElseThrow()

            if (jwtService.isTokenValid(refreshToken, user)) {
                val accessToken = jwtService.generateToken(user)
                revokeAllUserTokens(user)
                saveUserToken(user, accessToken)
                val authResponse = AuthenticationResponse(accessToken = accessToken, refreshToken = refreshToken, logins = loginService.getLast5Logins(user.id))
                ObjectMapper().writeValue(response.outputStream, authResponse)
            }
        }
    }

}