package com.nicoalex.todo.Form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLogTags
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.nicoalex.todo.R
import com.nicoalex.todo.databinding.ActivityFormBinding
import com.nicoalex.todo.databinding.ActivityMainBinding
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
            //findViewById<EditText>(R.id.TitleTask).setText(task.title.toString())
            //findViewById<EditText>(R.id.DescriptionTask).setText(task.description.toString())
            binding.DescriptionTask.setText(task.description.toString())
        }
        binding.submitEdit.setOnClickListener(){
            //val title = findViewById<EditText>(R.id.TitleTask).text.toString()
            //val description = findViewById<EditText>(R.id.DescriptionTask).text.toString()
            val title = binding.TitleTask.text.toString()
            val description = binding.DescriptionTask.text.toString()
            val newTask = Task(id = task?.id ?: UUID.randomUUID().toString(), title = title,description = description)
            intent.putExtra("task",newTask)
            setResult(RESULT_OK, intent)
            finish()
        }
        /*findViewById<Button>(R.id.submitEdit).setOnClickListener(){
            val title = findViewById<EditText>(R.id.TitleTask).text.toString()
            val description = findViewById<EditText>(R.id.DescriptionTask).text.toString()
            val newTask = Task(id = task?.id ?: UUID.randomUUID().toString(), title = title,description = description)
            intent.putExtra("task",newTask)
            setResult(RESULT_OK, intent)
            finish()
        }*/
    }
}