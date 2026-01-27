package org.example.security

import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.Header
import io.javalin.http.UnauthorizedResponse

object Auth: Handler {

    private val userRolesMap = mapOf(
        Pair("alice", "weak-1234") to listOf(Role.USER_READ),
        Pair("bob", "weak-123456") to listOf(Role.USER_READ, Role.USER_WRITE)
    )

    private val Context.userRoles: List<Role>
        get() = this.basicAuthCredentials()?.let { (username, password) ->
            userRolesMap[Pair(username, password)] ?: listOf()
        } ?: listOf()

    override fun handle(ctx: Context) {
        val permittedRoles = ctx.routeRoles()
        when {
            permittedRoles.contains(Role.ANYONE) -> return
            ctx.userRoles.any { it in permittedRoles } -> return
            else -> {
                ctx.header(Header.WWW_AUTHENTICATE, "Basic")
                throw UnauthorizedResponse()
            }
        }
    }
}