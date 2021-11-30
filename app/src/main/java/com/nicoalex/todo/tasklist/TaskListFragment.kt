package com.nicoalex.todo.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nicoalex.todo.Form.FormActivity
import com.nicoalex.todo.MainActivity
import com.nicoalex.todo.R
import java.util.*

class TaskListFragment : Fragment(){
    private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )
    private val formLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // ici on récupérera le résultat pour le traiter
        val task = result.data?.getSerializableExtra("task") as? Task
        if (task != null) {
            taskList.add(task)
        }else{
            Toast.makeText(context, "LUL", Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView  = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val myAdapter = TaskListAdapter(taskList)
        recyclerView.adapter = myAdapter


        view.findViewById<FloatingActionButton>(R.id.AddingTaskButton).setOnClickListener{
            val intent = Intent(activity, FormActivity::class.java)
            formLauncher.launch(intent)
            //taskList.add(Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}"))
            //myAdapter.notifyDataSetChanged()
        }
        myAdapter.onClickDelete = { task ->
            taskList.remove(task)
            myAdapter.notifyDataSetChanged()
        }

        myAdapter.onModifyTask = { task ->
            val intent = Intent(activity, FormActivity::class.java)
            intent.putExtra("task",task)
            startActivity(intent)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }
}