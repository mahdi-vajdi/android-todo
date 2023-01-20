package com.mahdivajdi.simpletodo.ui.task

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.R
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentTaskBinding
import com.mahdivajdi.simpletodo.ui.dueDateString
import com.mahdivajdi.simpletodo.ui.timeStampToDate
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.temporal.ChronoField


class TaskFragment : Fragment(), PopupMenu.OnMenuItemClickListener,
    DatePickerDialog.OnDateSetListener {

    private val args: TaskFragmentArgs by navArgs()

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(
            TaskRepository((activity?.application as App).database.taskDao()),
            args.taskId
        )
    }

    private var _binding: FragmentTaskBinding? = null
    private val binding: FragmentTaskBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTaskBinding.inflate(layoutInflater, container, false)
        binding.apply {
            lifecycleOwner = this@TaskFragment
            viewModel = taskViewModel
        }
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskViewModel.task.observe(viewLifecycleOwner) { task ->
            binding.apply {
                textFieldTaskDueDate.editText?.setText(if (task.dueDate > 0) dueDateString(task.dueDate) else "Set due date")
                // TODO: Set remind me section in this line
                // TODO: Set repeat section in this line
                textViewTaskDateCreated.text = "Date created: ${timeStampToDate(task.dateModified)}"
            }
        }

        binding.apply {

            textFieldTaskTitle.editText?.setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    taskViewModel.updateTitle(
                        textFieldTaskTitle.editText?.text.toString()
                    )
                }
            }

            textFieldTaskDetail.editText?.setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    taskViewModel.updateDetail(
                        textFieldTaskDetail.editText?.text.toString()
                    )
                }
            }

            textFieldTaskDueDate.editText?.setOnClickListener {
                PopupMenu(requireContext(), it).apply {
                    setOnMenuItemClickListener(this@TaskFragment)
                    inflate(R.menu.due_date_menu)
                    show()
                }
            }

            textFieldTaskRemindMe.editText?.setOnClickListener { }
            textFieldTaskRepeat.editText?.setOnClickListener { }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val today = LocalDate.now()
        return when (item?.itemId) {
            R.id.menuItem_dueDate_today -> {
                updateDueDate(today.toEpochDay())
                true
            }
            R.id.menuItem_dueDate_tomorrow -> {
                updateDueDate(today.plusDays(1).toEpochDay())
                true
            }
            R.id.menuItem_dueDate_nextWeek -> {
                val zdt = ZonedDateTime.now()
                val firstOfWeek: ZonedDateTime =
                    zdt.with(ChronoField.DAY_OF_WEEK, 1) // ISO 8601, Monday is first day of week.
                val firstOfNextWeek = firstOfWeek.plusWeeks(1)
                Log.i("calendar", "first day of the week: $firstOfWeek")
                updateDueDate(firstOfNextWeek.toLocalDate().toEpochDay())
                true
            }
            R.id.menuItem_dueDate_custom -> {
                DatePickerDialog(
                    requireContext(),
                    this,
                    today.year,
                    today.monthValue - 1,
                    today.dayOfMonth
                ).show()
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
        updateDueDate(LocalDate.of(year, month + 1, dayOfMonth).toEpochDay())
    }

    private fun updateDueDate(dueDate: Long) {
        taskViewModel.task.value?.let {
            taskViewModel.updateDueDate(it.taskId, dueDate)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}