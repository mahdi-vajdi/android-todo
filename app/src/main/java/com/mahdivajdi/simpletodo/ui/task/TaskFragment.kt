package com.mahdivajdi.simpletodo.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentTaskBinding
import com.mahdivajdi.simpletodo.domain.model.Task


class TaskFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            TaskRepository(
                (activity?.application as App).database.taskDao()
            )
        )
    }

    private var _binding: FragmentTaskBinding? = null
    private val binding: FragmentTaskBinding get() = _binding!!

    private val args: TaskFragmentArgs by navArgs()
    private lateinit var task: LiveData<Task>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTaskBinding.inflate(layoutInflater, container, false)
        task = taskViewModel.getTask(args.taskId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        task.observe(viewLifecycleOwner) { task ->
            binding.apply {
                textViewTaskTitle.text = task.title
                textViewTaskDescription.text = task.description
                textViewTaskTimestamp.text = task.timestamp.toString()
                buttonTaskDone.setOnClickListener {
                    taskViewModel.updateTask(task.apply { done = true })
                }
                buttonTaskDelete.setOnClickListener {
                    taskViewModel.deleteTask(task.taskId)
                }
                buttonTaskEdit.setOnClickListener {
                    val action = TaskFragmentDirections.actionTaskFragmentToEditTaskFragment(task.taskId)
                    view.findNavController().navigate(action)
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}