package org.shykhov.kotlinauthapp.exception.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class ErrorResponse(val message: List<String?>) {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now()
}
