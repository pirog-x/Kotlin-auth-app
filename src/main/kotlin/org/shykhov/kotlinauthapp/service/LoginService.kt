package org.shykhov.kotlinauthapp.service

import org.shykhov.kotlinauthapp.entity.Login
import org.shykhov.kotlinauthapp.entity.User
import org.shykhov.kotlinauthapp.repository.LoginRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class LoginService(
    val loginRepository: LoginRepository
) {
    fun save(user: User): Login {
        val userId = user.id
        val logins: List<Login> = loginRepository.findAllByUserId(userId)
        if (logins.size >= 5) {
            removeLoginById(getFirstLogin(logins).id)
        }
        val newLogin = Login(
            id = 0,
            loginTimestamp = Date(),
            user = user
        )
        return loginRepository.save(newLogin)
    }

    fun removeLoginById(id: Int) {
        loginRepository.deleteById(id)
    }

    fun getLast5Logins(userId: Int): List<Login> {
        val logins = loginRepository.findAllByUserId(userId)
        return logins
    }

    private fun getFirstLogin(logins: List<Login>): Login {
        var fistLogin = logins[0]
        logins.forEach { login ->
            if (login.loginTimestamp < fistLogin.loginTimestamp) {
                fistLogin = login
            }
        }
        return fistLogin
    }
}