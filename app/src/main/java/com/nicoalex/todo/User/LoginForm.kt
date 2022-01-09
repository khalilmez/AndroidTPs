package com.nicoalex.todo.User


import kotlinx.serialization.SerialName
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class LoginForm (
    @SerialName("email")
    val id: String,
    @SerialName("password")
    val password: String
    )