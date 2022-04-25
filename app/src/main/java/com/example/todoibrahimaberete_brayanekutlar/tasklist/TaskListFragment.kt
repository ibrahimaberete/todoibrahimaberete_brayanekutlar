package com.example.todoibrahimaberete_brayanekutlar.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoibrahimaberete_brayanekutlar.R
import com.example.todoibrahimaberete_brayanekutlar.databinding.FragmentTaskListBinding
import com.example.todoibrahimaberete_brayanekutlar.form.FormActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class TaskListFragment : Fragment() {
    val adapter = TaskListAdapter()
    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )
    val createTask =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // ici on récupérera le résultat pour le traiter
            val task = result.data?.getSerializableExtra("task") as? Task
                ?: return@registerForActivityResult
            taskList = taskList + task
            adapter.submitList(taskList)
        }
    val updateTask =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // ici on récupérera le résultat pour le traiter
            val task = result.data?.getSerializableExtra("task") as? Task
            if (task != null) {
                taskList = taskList.map {
                    if (it.id == task.id) task else it
                }
                adapter.submitList(taskList)
            }
        }
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter.submitList(taskList)
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        // Instanciation d'un objet task avec des données préremplies:
        val btn = binding.floatingActionButton
        btn.setOnClickListener {

            //  val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            //  taskList = taskList + newTask
            // adapter.submitList(taskList)

            val intent = Intent(context, FormActivity::class.java)
            createTask.launch(intent)

        }

        adapter.onClickDelete = { task ->
            // Supprimer la tâche
            taskList = taskList - task
            adapter.submitList(taskList)
        }

        adapter.onClickEdit = { task ->
            val intent = Intent(context, FormActivity::class.java)
            intent.putExtra("task", task)
            updateTask.launch(intent)


        }

    }


}