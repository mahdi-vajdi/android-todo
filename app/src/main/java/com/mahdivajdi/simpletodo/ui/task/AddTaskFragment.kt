package com.mahdivajdi.simpletodo.ui.task

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.R
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentAddTaskBinding
import com.mahdivajdi.simpletodo.domain.model.Category
import com.mahdivajdi.simpletodo.domain.model.Task
import com.mahdivajdi.simpletodo.ui.MainViewModel
import com.mahdivajdi.simpletodo.ui.TaskViewModelFactory
import java.time.Instant
import java.time.LocalDate

class AddTaskFragment : BottomSheetDialogFragment(), DatePickerDialog.OnDateSetListener,
    PopupMenu.OnMenuItemClickListener {

    companion object {
        fun newInstance() = AddTaskFragment()
    }

    private val mainViewModel: MainViewModel by activityViewModels {
        TaskViewModelFactory(TaskRepository((activity?.application as App).database.taskDao()),
            CategoryRepository((activity?.application as App).database.categoryDao()))
    }

    private var _binding: FragmentAddTaskBinding? = null
    private val binding: FragmentAddTaskBinding get() = _binding!!

    private lateinit var spinnerAdapter: ArrayAdapter<Category>

    // value of the optional due date property that user can choose
    private var date: Long = 0

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
        mainViewModel.getCategories().observe(this) { categories ->
            spinnerAdapter.addAll(categories)
            spinnerAdapter.notifyDataSetChanged()
        }

        binding.buttonAddTaskSchedule.setOnClickListener {
            PopupMenu(requireContext(), it).apply {
                setOnMenuItemClickListener(this@AddTaskFragment)
                inflate(R.menu.due_date_menu)
                show()
            }
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
                dueDate = date,
                priority = binding.checkBoxAddTaskPriority.isChecked
            )
            mainViewModel.insertTask(task)
            dismiss()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val today = LocalDate.now()
        return when (item?.itemId) {
            R.id.menuItem_dueDate_today -> {
                date = today.toEpochDay()
                true
            }
            R.id.menuItem_dueDate_tomorrow -> {
                date = today.plusDays(1).toEpochDay()
                true
            }
            R.id.menuItem_dueDate_nextWeek -> {
                date = today.plusWeeks(1).toEpochDay()
                true
            }
            R.id.menuItem_dueDate_custom -> {
                DatePickerDialog(requireContext(),
                    this,
                    today.year,
                    today.monthValue - 1,
                    today.dayOfMonth).show()
                /*
                Date object gets set in onDateSet() function because
                datePicker doesn't support chained listener in APIs below 24
                */
                true
            }
            else -> false
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        date = LocalDate.of(year, month + 1, dayOfMonth).toEpochDay()
        Log.d("pickers", "onDateSet:$year, $month, $dayOfMonth, date = $date")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}