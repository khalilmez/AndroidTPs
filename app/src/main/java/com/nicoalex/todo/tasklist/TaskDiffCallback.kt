package com.nicoalex.todo.tasklist

import androidx.recyclerview.widget.DiffUtil

object TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return oldItem.id == newItem.id
    }

        // are they the same "entity" ? (usually same id)
    override fun areContentsTheSame(oldItem: Task, newItem: Task) : Boolean {
            return ((oldItem.description == newItem.description) && (oldItem.title == newItem.title))
    }
    // do they have the same data ? (content)
}
