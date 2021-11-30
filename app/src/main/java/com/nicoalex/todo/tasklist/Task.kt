package com.nicoalex.todo.tasklist
import java.io.Serializable

data class Task(val id: String, var title: String, var description: String = "") : Serializable {

}