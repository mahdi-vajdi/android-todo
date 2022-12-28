package com.mahdivajdi.simpletodo.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mahdivajdi.simpletodo.R
import com.mahdivajdi.simpletodo.data.TaskRepository
import com.mahdivajdi.simpletodo.data.local.AppDatabase
import com.mahdivajdi.simpletodo.databinding.FragmentEditTaskBinding
import com.mahdivajdi.simpletodo.domain.model.TaskDomainModel
import kotlinx.coroutines.NonDisposableHandle.parent

class EditTaskFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            TaskRepository(
                AppDatabase.getDatabase(requireContext()).taskDao()
            )
        )
    }

    private var _binding: FragmentEditTaskBinding? = null
    private val binding: FragmentEditTaskBinding get() = _binding!!

    private val args: TaskFragmentArgs by navArgs()
    private lateinit var task: LiveData<TaskDomainModel>

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
                editTextEditTaskDescription.setText(task.description)
                buttonEditTaskSave.setOnClickListener {
                    val editedTask = task.apply {
                        title = editTextEditTaskTitle.text.toString()
                        description = editTextEditTaskDescription.text.toString()
                    }
                    taskViewModel.updateTask(editedTask)
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