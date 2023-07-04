package org.shykhov.kotlinauthapp.repository

import org.shykhov.kotlinauthapp.entity.Login
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LoginRepository : JpaRepository<Login, Int> {
    @Query(value = """
            SELECT l FROM Login l
            WHERE l.user.id = :userId 
        """)
    fun findAllByUserId(@Param("userId") userId: Int): List<Login>
}