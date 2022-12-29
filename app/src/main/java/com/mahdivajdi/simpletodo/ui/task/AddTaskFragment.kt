package com.mahdivajdi.simpletodo.ui.task

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.mahdivajdi.simpletodo.databinding.AddTaskDialogBinding
import com.mahdivajdi.simpletodo.domain.model.Task
import java.time.Instant

class AddTaskFragment(private val newTask: (task: Task) -> Unit) : DialogFragment() {

    private var _binding: AddTaskDialogBinding? = null
    private val binding: AddTaskDialogBinding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            _binding = AddTaskDialogBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
                .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                    val instant = Instant.now()
                    // TODO: Fix the absence of category id in the task model
                    val task = Task(
                        title = binding.editTextAddTaskTitle.text.toString(),
                        description = binding.editTextAddTaskDescription.text.toString(),
                        timestamp = instant.epochSecond,
                    )
                    newTask(task)
                    dialog?.cancel()
                }).setNegativeButton("Cancel" ,DialogInterface.OnClickListener { _, _ ->
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