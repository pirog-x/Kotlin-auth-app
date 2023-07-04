package org.shykhov.kotlinauthapp.repository

import org.shykhov.kotlinauthapp.entity.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TokenRepository : JpaRepository<Token, Int> {
    @Query(value = """
      SELECT t FROM Token t INNER JOIN User u 
      ON t.user.id = u.id 
      WHERE u.id = :id AND (t.expired = false OR t.revoked = false) 
      """)
    fun findAllValidTokenByUser(id: Int): List<Token>
    fun findByToken(token: String): Optional<Token>
}