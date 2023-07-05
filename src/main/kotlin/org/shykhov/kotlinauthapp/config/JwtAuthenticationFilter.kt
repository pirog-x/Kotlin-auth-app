package org.shykhov.kotlinauthapp.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Validator
import lombok.RequiredArgsConstructor
import org.shykhov.kotlinauthapp.entity.RegisterRequest
import org.shykhov.kotlinauthapp.exception.model.ErrorResponse
import org.shykhov.kotlinauthapp.repository.TokenRepository
import org.shykhov.kotlinauthapp.service.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.security.SignatureException

@Component
@RequiredArgsConstructor
class JwtAuthenticationFilter @Autowired constructor (
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
    private val tokenRepository: TokenRepository,
    private val objectMapper: ObjectMapper,
    private val validator: Validator
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain
    ) {
        try {
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
        } catch (ex: SignatureException) {
            logger.info("User do request with bad jwt.")
            handleInvalidCorrelationId(response, ex)
            return
        } catch (ex: MalformedJwtException) {
            logger.info("User do request with malformed jwt.")
            handleInvalidCorrelationId(response, ex)
            return
        } catch (ex: ExpiredJwtException) {
            logger.info("User do request with expired jwt.")
            handleInvalidCorrelationId(response, ex)
            return
        } catch (ex: UsernameNotFoundException) {
            logger.info("User email from jwt token doesn't exist.")
            handleInvalidCorrelationId(response, ex)
            return
        }
        filterChain.doFilter(request, response)
    }

    @Throws(IOException::class)
    private fun handleInvalidCorrelationId(response: HttpServletResponse, ex: Exception) {
        val errorResponse = ErrorResponse(listOf(ex.message))

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_BAD_REQUEST
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}
