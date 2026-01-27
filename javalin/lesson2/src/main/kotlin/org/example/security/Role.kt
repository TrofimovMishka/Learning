package org.example.security

import io.javalin.security.RouteRole

enum class Role : RouteRole {
    ANYONE, USER_READ, USER_WRITE
}
