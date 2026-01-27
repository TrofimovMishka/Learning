package org.example

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.example.controller.UserController
import org.example.security.Auth
import org.example.security.Role

fun main() {
    Javalin.create { config ->
        config.router.mount { routing ->
            routing.beforeMatched(
                path = "/",
                handler = Auth::handle
            )
        }.apiBuilder {
            get("/", { ctx -> ctx.redirect("/users") }, Role.ANYONE)
            path("users") {
                get(UserController::getAllUserIds, Role.ANYONE)
                post(UserController::createUser, Role.USER_WRITE)
                path("{userId}") {
                    get(UserController::getUser, Role.USER_READ)
                    patch(UserController::updateUser, Role.USER_WRITE)
                    delete(UserController::deleteUser, Role.USER_WRITE)
                }
            }
        }
    }.start(8877)
}

