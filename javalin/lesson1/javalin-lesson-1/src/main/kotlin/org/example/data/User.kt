package org.example.data

import java.util.UUID
import kotlin.uuid.Uuid

data class User(val name: String, val email: String, val id: UUID = UUID.randomUUID())
