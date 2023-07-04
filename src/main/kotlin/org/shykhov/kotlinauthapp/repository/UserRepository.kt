package org.shykhov.kotlinauthapp.repository

import org.shykhov.kotlinauthapp.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): Optional<User>
}
