package org.shykhov.kotlinauthapp.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
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

    @NotBlank(message = "Login timestamp cannot be blank.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    val loginTimestamp: Date,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
)