package org.shykhov.kotlinauthapp.entity

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Builder


@Builder
class AuthenticationResponse (
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("Last_five_logins")
    val logins: List<Login>
)