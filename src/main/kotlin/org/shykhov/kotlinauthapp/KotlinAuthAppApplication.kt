package org.shykhov.kotlinauthapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinAuthAppApplication

fun main(args: Array<String>) {
    runApplication<KotlinAuthAppApplication>(*args)
}
