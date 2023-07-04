package org.shykhov.kotlinauthapp.entity

import lombok.*

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class AuthenticationRequest (
    val email: String,
    val password: String
)