package com.mahdivajdi.simpletodo.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mahdivajdi.simpletodo.databinding.AddTaskDialogBinding
import com.mahdivajdi.simpletodo.domain.model.TaskDomainModel
import java.time.Instant

class AddTaskFragment(private val newTask: (task: TaskDomainModel) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val binding = AddTaskDialogBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
                .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                    val instant = Instant.now()
                    val task = TaskDomainModel(
                        title = binding.editTextAddTaskTitle.text.toString(),
                        description = binding.editTextAddTaskDescription.text.toString(),
                        timestamp = instant.epochSecond,
                        tag = binding.editTextAddTaskTtags.text.toString()
                    )
                    newTask(task)
                    dialog?.cancel()
                }).setNegativeButton("Cancel" ,DialogInterface.OnClickListener { _, _ ->
                    dialog?.cancel()
                })
            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}