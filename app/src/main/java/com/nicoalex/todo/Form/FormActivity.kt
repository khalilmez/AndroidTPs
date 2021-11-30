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
import com.nicoalex.todo.tasklist.Task
import java.util.*

class FormActivity : AppCompatActivity() {
    lateinit var taskfinal : Task
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // ici on récupérera le résultat pour le traiter
            Toast.makeText(this,"Hello",Toast.LENGTH_LONG).show()
            val task = result.data?.getSerializableExtra("task") as? Task
            if (task != null) {
                Toast.makeText(this, "WE MADE IT !", Toast.LENGTH_LONG).show()
                taskfinal = task
                findViewById<EditText>(R.id.TitleTask).setText(task.title.toString())
                findViewById<EditText>(R.id.DescriptionTask).setText(task.description.toString())
            }
        }

        findViewById<Button>(R.id.submitEdit).setOnClickListener(){
            val title = findViewById<EditText>(R.id.TitleTask).text.toString()
            val description = findViewById<EditText>(R.id.DescriptionTask).text.toString()
            if(this::taskfinal.isInitialized){
                Toast.makeText( this, "Nice Modification " + taskfinal.title + " " + title ,Toast.LENGTH_LONG).show()
                taskfinal.title = title
                taskfinal.description = description
                intent.putExtra("task", taskfinal)
            }else{
                Toast.makeText(this, "new one" , Toast.LENGTH_LONG).show()
                val newTask = Task(id = UUID.randomUUID().toString(), title = title, description = description)
                intent.putExtra("task", newTask)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}