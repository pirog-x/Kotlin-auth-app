package org.shykhov.kotlinauthapp.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor

@Builder
@AllArgsConstructor
@NoArgsConstructor
data class RegisterRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val role: Role = Role.USER
)