package org.shykhov.kotlinauthapp.service

import org.shykhov.kotlinauthapp.exception.EntityNotFoundException
import org.shykhov.kotlinauthapp.entity.User
import org.shykhov.kotlinauthapp.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    val userRepository: UserRepository
) {

    fun getUser(username: String): User {
        val user: Optional<User> = userRepository.findByEmail(username)
        return unwrapUser(user, 404L)
    }

    fun unwrapUser(entity: Optional<User>, id: Long): User {
        return if (entity.isPresent) entity.get()
        else throw EntityNotFoundException(id, User::class.java)
    }
}