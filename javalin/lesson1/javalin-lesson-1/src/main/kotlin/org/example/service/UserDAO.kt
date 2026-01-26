package org.example.service

import org.example.data.User
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class UserDAO {

    val logger = LoggerFactory.getLogger(UserDAO::class.java)

    val users = hashMapOf(
        0 to User("Bob", "test_mail_1@test.com", UUID.randomUUID()),
        1 to User("George", "test_mail_2@test.com", UUID.randomUUID()),
        2 to User("Rahit", "test_mail_3@test.com", UUID.randomUUID()),
        3 to User("Reni", "test_mail_4@test.com", UUID.randomUUID()),
        4 to User("House", "test_mail_5@test.com", UUID.randomUUID()),
    )

    val lastId: AtomicInteger = AtomicInteger(users.size - 1)

    fun saveUser(name: String, email: String) {
        val id = lastId.incrementAndGet()
        users[id] = User(name, email)

        logger.info("User was created {}", users[id])
    }
}