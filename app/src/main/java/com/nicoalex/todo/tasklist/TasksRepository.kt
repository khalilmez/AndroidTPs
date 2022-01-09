package com.nicoalex.todo.tasklist

import android.util.Log
import com.nicoalex.todo.network.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response

class TasksRepository {
    private val tasksWebService = Api.tasksWebService

    suspend fun refresh() : List<Task>? {
        val tasksResponse = tasksWebService.getTasks()
        if (tasksResponse.isSuccessful) {
            return tasksResponse.body()
        }
        Log.e("TasksRepository", "Error while fetching tasks: ${tasksResponse.message()}")
        return null
    }

    suspend fun delete(task: Task): Boolean{
        val response = tasksWebService.delete(task.id)
        if(response.isSuccessful){
            return true
        }
        return false
    }

    suspend fun createOrUpdate(task: Task) : Task?{
        val taskListTemp = tasksWebService.getTasks()
        if(taskListTemp.isSuccessful){
            val taskTemp = taskListTemp.body()?.firstOrNull { it.id == task.id }
            val response : Response<Task>
            if(taskTemp != null){
                response = tasksWebService.update(task)
            }else{
                response = tasksWebService.create(task)
            }
            if(response.isSuccessful){
                return response.body()
            }
            return null
        }
        return null
    }
}
