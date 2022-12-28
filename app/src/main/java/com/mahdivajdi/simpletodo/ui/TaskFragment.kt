package com.mahdivajdi.simpletodo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.navigation.fragment.navArgs
import com.mahdivajdi.simpletodo.data.TaskRepository
import com.mahdivajdi.simpletodo.data.local.AppDatabase
import com.mahdivajdi.simpletodo.databinding.FragmentTaskBinding
import com.mahdivajdi.simpletodo.domain.model.TaskDomainModel


class TaskFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            TaskRepository(
                AppDatabase.getDatabase(requireContext()).taskDao()
            )
        )
    }

    private var _binding: FragmentTaskBinding? = null
    private val binding: FragmentTaskBinding get() = _binding!!

    private val args: TaskFragmentArgs by navArgs()
    private lateinit var task: LiveData<TaskDomainModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTaskBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        task = taskViewModel.getTask(args.taskId)
        task.observe(viewLifecycleOwner) { task ->
            binding.apply {
                editTextTaskTitle.text = task.title
                editTextTaskDescription.text = task.description
                editTextTaskTimestamp.text = task.timestamp.toString()
                buttonTaskDone.setOnClickListener {
                    taskViewModel.updateTask(task.apply { done = true })
                }
                buttonTaskDelete.setOnClickListener {
                    taskViewModel.deleteTask(task)
                }
            }
        }

    }

}