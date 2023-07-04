package org.shykhov.kotlinauthapp.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.shykhov.kotlinauthapp.entity.Token
import org.shykhov.kotlinauthapp.repository.TokenRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service

@Service
class LogoutService(
    val tokenRepository: TokenRepository
) : LogoutHandler {
    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val authHeader = request?.getHeader("Authorization")
        val jwt: String
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return
        }
        jwt = authHeader.substring(7)
        val storedToken: Token? = tokenRepository.findByToken(jwt).orElse(null)

        if (storedToken != null) {
            storedToken.expired = true
            storedToken.revoked = true
            tokenRepository.save(storedToken)
            SecurityContextHolder.clearContext()
        }
    }
}