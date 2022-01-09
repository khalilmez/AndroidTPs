package com.nicoalex.todo.Form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nicoalex.todo.databinding.ActivityFormBinding
import com.nicoalex.todo.tasklist.Task
import java.util.*

class FormActivity : AppCompatActivity() {
    lateinit var taskfinal : Task
    private lateinit var binding: ActivityFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        val task = this.intent.getSerializableExtra("task") as? Task

        if (task != null) {
            taskfinal = task
            binding.TitleTask.setText(task.title.toString())
            binding.DescriptionTask.setText(task.description.toString())
        }
        binding.submitEdit.setOnClickListener(){
            val title = binding.TitleTask.text.toString()
            val description = binding.DescriptionTask.text.toString()
            val newTask = Task(id = task?.id ?: UUID.randomUUID().toString(), title = title,description = description)
            intent.putExtra("task",newTask)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}