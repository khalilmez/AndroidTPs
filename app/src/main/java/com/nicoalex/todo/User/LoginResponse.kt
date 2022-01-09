package com.nicoalex.todo.User

import kotlinx.serialization.SerialName
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class LoginResponse(
    @SerialName("token")
    val id: String
)