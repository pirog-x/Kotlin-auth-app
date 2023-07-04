package org.shykhov.kotlinauthapp.service

import org.shykhov.kotlinauthapp.exception.model.EntityNotFoundException
import org.shykhov.kotlinauthapp.entity.User
import org.shykhov.kotlinauthapp.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    val userRepository: UserRepository
) {

    fun get(email: String): User {
        val user: Optional<User> = userRepository.findByEmail(email)
        return unwrapUser(user, email)
    }

    fun save(user: User): User {
        return userRepository.save(user)
    }

    fun isUserExist(email: String): Boolean {
        val user = userRepository.findByEmail(email)
        return user.isPresent
    }

    fun findByEmail(email: String): Optional<User> {
        return userRepository.findByEmail(email)
    }

    private fun unwrapUser(entity: Optional<User>, id: String): User {
        return if (entity.isPresent) entity.get()
        else throw EntityNotFoundException(id, User::class.java)
    }
}