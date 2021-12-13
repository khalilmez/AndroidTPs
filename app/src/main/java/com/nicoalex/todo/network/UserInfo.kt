package com.nicoalex.todo.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class UserInfo(
    @SerialName("email")
    val email: String,
    @SerialName("firstname")
    val firstName: String,
    @SerialName("lastname")
    val lastName: String
)
