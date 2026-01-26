package org.example

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.HttpStatus
import io.javalin.http.bodyAsClass
import org.example.data.User
import org.example.service.UserDAO

fun main(args: Array<String>) {

    val userDAO = UserDAO()

    val userController = Javalin.create {
        it.router.apiBuilder {
            get("/") { it.redirect("/users") }

            get("/users") { ctx ->
                ctx.json(userDAO.users)
            }

            post("/users") { ctx ->
                val user = ctx.bodyAsClass<User>()
                userDAO.saveUser(user.name, user.email)
                ctx.status(201)
            }
        }
    }.apply {
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(HttpStatus.NOT_FOUND) { ctx -> ctx.json("Resource not found") }
    }.start(8877)
}

