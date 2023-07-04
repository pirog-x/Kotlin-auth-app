package org.shykhov.kotlinauthapp.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/demo-controller")
class DemoController {
    @GetMapping
    fun helloWorld(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello world. Secure message.")
    }
}