package org.shykhov.kotlinauthapp.entity

import lombok.Builder


@Builder
class AuthenticationResponse (
    val token: String
)