package org.shykhov.kotlinauthapp.entity

import lombok.Builder

@Builder
data class RegisterRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
)