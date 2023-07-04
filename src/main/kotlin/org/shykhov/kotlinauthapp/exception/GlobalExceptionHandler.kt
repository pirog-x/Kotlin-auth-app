package org.shykhov.kotlinauthapp.exception

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.shykhov.kotlinauthapp.exception.model.EntityNotFoundException
import org.shykhov.kotlinauthapp.exception.model.ErrorResponse
import org.shykhov.kotlinauthapp.exception.model.UserAlreadyExistException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: RuntimeException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(listOf(ex.message))
        return ResponseEntity(errorResponse, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(value = [MalformedJwtException::class, ExpiredJwtException::class])
    fun handleJwtExceptions(ex: RuntimeException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(listOf(ex.message))
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(UserAlreadyExistException::class)
    fun handleUserAlreadyExistException(ex: RuntimeException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(listOf(ex.message))
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}