package org.shykhov.kotlinauthapp.entity

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@AllArgsConstructor
@NoArgsConstructor
data class RegisterRequest(
    @NotBlank(message = "Firstname cannot be blank.")
    val firstname: String,

    @NotBlank(message = "Lastname cannot be blank.")
    val lastname: String,

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    val email: String,

    @NotBlank(message = "Email cannot be blank.")
    @Min(8, message = "Password must be at least 8 characters.")
    val password: String,
    val role: Role = Role.USER
)