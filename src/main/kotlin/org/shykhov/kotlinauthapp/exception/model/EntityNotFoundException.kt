package org.shykhov.kotlinauthapp.exception.model

import java.util.*

class EntityNotFoundException(id: String, entity: Class<*>) : RuntimeException(
    "The " + entity.simpleName.lowercase(Locale.getDefault()) + " with id '" + id + "' does not exist in our records."
)