package com.mahdivajdi.simpletodo.ui.task

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.AddTaskDialogBinding
import com.mahdivajdi.simpletodo.domain.model.Category
import com.mahdivajdi.simpletodo.domain.model.Task
import java.time.Instant

class AddTaskFragment(private val newTask: (task: Task) -> Unit) : DialogFragment() {

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(TaskRepository((activity?.application as App).database.taskDao()),
            CategoryRepository((activity?.application as App).database.categoryDao()))
    }

    private var _binding: AddTaskDialogBinding? = null
    private val binding: AddTaskDialogBinding get() = _binding!!

    private lateinit var spinnerAdapter: ArrayAdapter<Category>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = AddTaskDialogBinding.inflate(layoutInflater)

        // Initiate spinner
        spinnerAdapter =
            ArrayAdapter<Category>(requireContext(), android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAddTaskCategory.adapter = spinnerAdapter

        // Initiate list for category names and ids
        taskViewModel.getCategories().observe(this) { categories ->
            spinnerAdapter.addAll(categories)
            spinnerAdapter.notifyDataSetChanged()
        }

        return activity?.let { fragmentActivity ->
            val builder = AlertDialog.Builder(fragmentActivity)
            builder.setView(binding.root)
                .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                    val instant = Instant.now()
                    val spinner = binding.spinnerAddTaskCategory
                    val task = Task(
                        taskCategoryId = (spinner.getItemAtPosition(spinner.selectedItemPosition) as Category).categoryId,
                        title = binding.editTextAddTaskTitle.text.toString(),
                        detail = binding.editTextAddTaskDescription.text.toString(),
                        dateModified = instant.epochSecond,
                        state = false,
                        schedule = 0,
                        priority = false
                    )
                    newTask(task)
                    dialog?.cancel()
                }).setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
                    dialog?.cancel()
                })
            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}