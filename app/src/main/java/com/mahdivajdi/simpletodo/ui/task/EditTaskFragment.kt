package com.mahdivajdi.simpletodo.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentEditTaskBinding
import com.mahdivajdi.simpletodo.domain.model.Task

class EditTaskFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            TaskRepository((activity?.application as App).database.taskDao()),
            CategoryRepository((activity?.application as App).database.categoryDao())
        )
    }

    private var _binding: FragmentEditTaskBinding? = null
    private val binding: FragmentEditTaskBinding get() = _binding!!

    private val args: TaskFragmentArgs by navArgs()
    private lateinit var task: LiveData<Task>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditTaskBinding.inflate(layoutInflater, container, false)
        task = taskViewModel.getTask(args.taskId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        task.observe(viewLifecycleOwner) { task ->
            binding.apply {
                editTextEditTaskTitle.setText(task.title)
                editTextEditTaskDescription.setText(task.detail)
                buttonEditTaskSave.setOnClickListener {
                    taskViewModel.updateTask(Task(
                        taskId = task.taskId,
                        taskCategoryId = task.taskCategoryId,
                        title = editTextEditTaskTitle.text.toString(),
                        detail = editTextEditTaskDescription.text.toString(),
                        dateModified = task.dateModified,
                        state = task.state,
                        schedule = 0,
                        priority = false
                    ))
                    findNavController().popBackStack()
                }
                buttonEditTaskCancel.setOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}