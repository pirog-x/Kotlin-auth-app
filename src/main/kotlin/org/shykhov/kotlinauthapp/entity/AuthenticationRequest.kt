package org.shykhov.kotlinauthapp.entity

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import lombok.*

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class AuthenticationRequest (
    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    val email: String,
    @Min(8, message = "Password must be at least 8 characters.")
    val password: String
)