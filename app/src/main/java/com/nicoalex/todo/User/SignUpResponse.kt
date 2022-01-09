package com.nicoalex.todo.User

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse (
    @SerialName("token")
    val id: String
)