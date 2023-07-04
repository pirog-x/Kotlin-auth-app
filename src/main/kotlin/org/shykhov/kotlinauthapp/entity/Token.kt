package org.shykhov.kotlinauthapp.entity

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import lombok.experimental.FieldDefaults

@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
data class Token (
    @Id
    @GeneratedValue
    val id: Int,

    @Column(unique = true)
    val token: String,

    @Enumerated(EnumType.STRING)
    val tokenType: TokenType = TokenType.BEARER,

    var revoked: Boolean,
    var expired: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
)