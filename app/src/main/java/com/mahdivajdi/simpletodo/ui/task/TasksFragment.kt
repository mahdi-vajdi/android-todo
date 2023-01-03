package com.mahdivajdi.simpletodo.ui.task

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentTasksBinding
import com.mahdivajdi.simpletodo.ui.adapter.TaskListAdapter


class TasksFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels {
       TaskViewModelFactory(
           TaskRepository((activity?.application as App).database.taskDao()),
           CategoryRepository((activity?.application as App).database.categoryDao())
       )
    }

    private var _binding: FragmentTasksBinding? = null
    private val binding: FragmentTasksBinding get() = _binding!!

    private lateinit var taskListAdapter: TaskListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTasksBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initiate recyclerview
        taskListAdapter = TaskListAdapter { taskId ->
            val action = TasksFragmentDirections.actionTasksFragmentToTaskFragment(taskId)
            view.findNavController().navigate(action)
        }
        binding.recyclerViewMain.adapter = taskListAdapter
        binding.recyclerViewMain.layoutManager = LinearLayoutManager(requireContext())
        taskViewModel.getTasks().observe(viewLifecycleOwner) {
            taskListAdapter.submitList(it)
        }

       /* binding.fabMainAdd.setOnClickListener {
            val addTaskFragment = AddTaskFragment {
                Log.i("task", "onViewCreated: newTask= $it")
                taskViewModel.insertTask(it)
            }
            addTaskFragment.show(parentFragmentManager, "add_task")
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}