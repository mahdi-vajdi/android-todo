package com.mahdivajdi.simpletodo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahdivajdi.simpletodo.data.TaskRepository

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

}

class TaskViewModelFactory(private val taskRepository: TaskRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(
                taskRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

