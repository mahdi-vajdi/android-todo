package com.mahdivajdi.simpletodo.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentTaskBinding
import com.mahdivajdi.simpletodo.domain.model.Task
import com.mahdivajdi.simpletodo.ui.MainViewModel
import com.mahdivajdi.simpletodo.ui.TaskViewModelFactory
import com.mahdivajdi.simpletodo.ui.category.CategoriesFragmentDirections


class TaskFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels {
        TaskViewModelFactory(
            TaskRepository((activity?.application as App).database.taskDao()),
            CategoryRepository((activity?.application as App).database.categoryDao())
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
        task = mainViewModel.getTask(args.taskId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        task.observe(viewLifecycleOwner) { task ->
            binding.apply {
                textViewTaskTitle.text = task.title
                textViewTaskDescription.text = task.detail
                textViewTaskTimestamp.text = task.dateModified.toString()
                buttonTaskDone.setOnClickListener {
                    mainViewModel.updateTask(Task(
                        taskId = task.taskId,
                        taskCategoryId = task.taskCategoryId,
                        title = task.title,
                        detail = task.detail,
                        dateModified = task.dateModified,
                        state = true,
                        schedule = task.schedule,
                        priority = task.priority
                    ))
                }
                buttonTaskDelete.setOnClickListener {
                    mainViewModel.deleteTask(task.taskId)
                }
                buttonTaskEdit.setOnClickListener {
                    val action = CategoriesFragmentDirections.actionCategoriesFragmentToEditTaskFragment(task.taskId)
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