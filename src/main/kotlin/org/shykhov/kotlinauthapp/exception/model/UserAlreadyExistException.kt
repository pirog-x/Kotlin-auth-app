package org.shykhov.kotlinauthapp.exception.model

class UserAlreadyExistException(email: String) : RuntimeException(
    "User with email: '$email' is already exist."
)