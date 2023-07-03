package org.shykhov.kotlinauthapp.dao.repository

import org.shykhov.kotlinauthapp.dao.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): Optional<User>
}
