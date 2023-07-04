package org.shykhov.kotlinauthapp.exception

import java.util.*

class EntityNotFoundException(id: Long, entity: Class<*>) : RuntimeException(
    "The " + entity.simpleName.lowercase(Locale.getDefault()) + " with id '" + id + "' does not exist in our records."
)