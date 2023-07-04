package org.shykhov.kotlinauthapp.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.experimental.FieldDefaults
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
data class Login (
    @Id
    @GeneratedValue
    val id: Int,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    val loginTimestamp: Date,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
)