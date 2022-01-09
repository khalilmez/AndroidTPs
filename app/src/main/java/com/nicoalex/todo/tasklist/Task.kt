package com.nicoalex.todo.tasklist

import kotlinx.serialization.SerialName
import kotlinx.serialization.*

@Serializable
data class Task(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String = "This is a task!"
) : java.io.Serializable
{}