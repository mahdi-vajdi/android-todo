package com.mahdivajdi.simpletodo.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.R
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentAddTaskBinding
import com.mahdivajdi.simpletodo.domain.model.Category
import com.mahdivajdi.simpletodo.domain.model.Task
import java.time.Instant

class AddTaskFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = AddTaskFragment()
    }

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(TaskRepository((activity?.application as App).database.taskDao()),
            CategoryRepository((activity?.application as App).database.categoryDao()))
    }

    private var _binding: FragmentAddTaskBinding? = null
    private val binding: FragmentAddTaskBinding get() = _binding!!

    private lateinit var spinnerAdapter: ArrayAdapter<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_SimpleTodo_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initiate spinner
        spinnerAdapter =
            ArrayAdapter<Category>(requireContext(), android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAddTaskCategory.adapter = spinnerAdapter
        // get list for category names and ids
        taskViewModel.getCategories().observe(this) { categories ->
            spinnerAdapter.addAll(categories)
            spinnerAdapter.notifyDataSetChanged()
        }

        binding.buttonAddTaskSave.setOnClickListener {
            val instant = Instant.now()
            val spinner = binding.spinnerAddTaskCategory
            val task = Task(
                taskCategoryId = (spinner.getItemAtPosition(spinner.selectedItemPosition) as Category).categoryId,
                title = binding.editTextAddTaskTitle.text.toString(),
                detail = binding.editTextAddTaskDetail.text.toString(),
                dateModified = instant.epochSecond,
                state = false,
                schedule = 0,
                priority = binding.checkBoxAddTaskPriority.isSelected
            )
            taskViewModel.insertTask(task)
            dismiss()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}