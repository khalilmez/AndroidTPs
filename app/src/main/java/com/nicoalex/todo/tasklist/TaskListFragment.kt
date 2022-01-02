package com.nicoalex.todo.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nicoalex.todo.Form.FormActivity
import com.nicoalex.todo.R
import com.nicoalex.todo.databinding.ActivityMainBinding
import com.nicoalex.todo.databinding.FragmentTaskListBinding
import java.util.*
import com.nicoalex.todo.tasklist.Task
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


class TaskListFragment : Fragment() {

    /*private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    );*/

    private val adapter = TaskListAdapter()

    private val viewModel: TaskListViewModel by viewModels()

    private val formLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        val newTask = result.data?.getSerializableExtra("task") as Task;

        lifecycleScope.launch {
            viewModel.addOrEdit(newTask)
        }
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
        //val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity);
        recyclerView.adapter = adapter;
        val addButton = view.findViewById<FloatingActionButton>(R.id.AddingTaskButton);
        //val addButton = binding.AddingTaskButton
        addButton.setOnClickListener {
            formLauncher.launch(Intent(activity, FormActivity::class.java));
        };
        adapter.onClickDelete = { task ->
            lifecycleScope.launch {
                viewModel.delete(task)
            }
            adapter.notifyDataSetChanged();
        };

        adapter.onModifyTask = { task ->
            val intent = Intent(activity, FormActivity::class.java)
            val bundle = Bundle()
            intent.putExtra("task", task)
            formLauncher.launch(intent)
        }/*
        lifecycleScope.launch {
            tasksRepository.taskList.collect { newList->
                adapter.setTaskList(newList)
                adapter.notifyDataSetChanged()
            }
        }*/
        lifecycleScope.launch {
            viewModel.taskList.collect { newList ->
                adapter.submitList(newList)
                adapter.notifyDataSetChanged()
            }
        }

    }
    private lateinit var binding: FragmentTaskListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTaskListBinding.inflate(layoutInflater)

 //       binding = ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.refresh()
        }
    }
}
