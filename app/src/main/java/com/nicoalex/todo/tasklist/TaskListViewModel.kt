package com.nicoalex.todo.tasklist

import android.content.Intent
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicoalex.todo.User.AuthenticationActivity
import com.nicoalex.todo.User.SHARED_PREF_TOKEN_KEY
import com.nicoalex.todo.network.Api
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {
    private val repository = TasksRepository()

    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    public val taskList: StateFlow<List<Task>> = _taskList

    fun refresh() {
        viewModelScope.launch {
            val taskListTemp = repository.refresh()
            if (taskListTemp != null) {
                _taskList.value = taskListTemp
            }
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            if(repository.delete(task)){
                _taskList.value = taskList.value - task
            }
        }
    }

    fun addOrEdit(task: Task) {
        viewModelScope.launch {
            val oldTask = taskList.value.firstOrNull { it.id == task.id }
            val taskTemp = repository.createOrUpdate(task)
            if(taskTemp != null) {
                if(oldTask != null) {
                    _taskList.value = taskList.value - oldTask
                }
                _taskList.value = taskList.value + taskTemp
            }
        }
    }
}