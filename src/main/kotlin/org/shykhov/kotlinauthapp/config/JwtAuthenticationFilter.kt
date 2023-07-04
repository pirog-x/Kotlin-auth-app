package org.shykhov.kotlinauthapp.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.shykhov.kotlinauthapp.repository.TokenRepository
import org.shykhov.kotlinauthapp.service.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@RequiredArgsConstructor
class JwtAuthenticationFilter @Autowired constructor (
    val jwtService: JwtService,
    val userDetailsService: UserDetailsService,
    val tokenRepository: TokenRepository,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response)
            return
        }
        val jwtToken: String = authHeader.substring(7)
        val userEmail: String? = jwtService.extractUsername(jwtToken)
        if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(userEmail)
            val isTokenValid = tokenRepository.findByToken(jwtToken)
                .map{t -> !t.expired && !t.revoked}
                .orElse(false)
            if (jwtService.isTokenValid(jwtToken, userDetails) && isTokenValid) {
                val authToken= UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)
    }
}
