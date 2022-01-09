package com.nicoalex.todo.tasklist

import androidx.recyclerview.widget.DiffUtil

object TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task) : Boolean {
            return ((oldItem.description == newItem.description) && (oldItem.title == newItem.title))
    }
}
