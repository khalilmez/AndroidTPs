package com.nicoalex.todo.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nicoalex.todo.Form.FormActivity
import com.nicoalex.todo.R
import java.util.*
import com.nicoalex.todo.tasklist.Task
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


class TaskListFragment : Fragment() {

    // The list of default tasks.
    /*private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    );*/
    private val tasksRepository = TasksRepository()

    private val adapter = TaskListAdapter(tasksRepository.taskList.value)

    private val formLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        val newTask = result.data?.getSerializableExtra("task") as Task;

        lifecycleScope.launch {
            tasksRepository.createOrUpdate(newTask)
        }

        // In any case, notify for changes!
        adapter.notifyDataSetChanged()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false);
        return rootView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view);
        recyclerView.layoutManager = LinearLayoutManager(activity);
        recyclerView.adapter = adapter;

        val addButton = view.findViewById<FloatingActionButton>(R.id.AddingTaskButton);
        addButton.setOnClickListener {
            formLauncher.launch(Intent(activity, FormActivity::class.java));
        };

        adapter.onClickDelete = { task ->
            lifecycleScope.launch {
                tasksRepository.delete(task)
            }
            adapter.notifyDataSetChanged();
        };

        adapter.onModifyTask = { task ->
            val intent = Intent(activity, FormActivity::class.java)
            val bundle = Bundle()
            intent.putExtra("task", task)
            formLauncher.launch(intent)
        }
        lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
            tasksRepository.taskList.collect { newList->
                adapter.setTaskList(newList)
                adapter.notifyDataSetChanged()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            tasksRepository.refresh() // on demande de rafraîchir les données sans attendre le retour directement
        }

    }
}
