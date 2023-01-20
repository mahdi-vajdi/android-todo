package com.mahdivajdi.simpletodo.ui.task

import android.view.View
import androidx.lifecycle.*
import com.google.android.material.checkbox.MaterialCheckBox
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.domain.model.Task
import kotlinx.coroutines.launch


class TaskViewModel(private val taskRepository: TaskRepository, private val taskId: Long) :
    ViewModel() {

    val task: LiveData<Task> = taskRepository.getTask(taskId).asLiveData()

    fun updateTitle(title: String) {
        viewModelScope.launch {
            taskRepository.updateTaskTitle(taskId, title)
        }
    }

    fun updateDetail(detail: String) {
        viewModelScope.launch {
            taskRepository.updateTaskDetail(taskId, detail)
        }
    }

    fun updateState(view: View) {
        if (view is MaterialCheckBox) {
            viewModelScope.launch {
                taskRepository.updateTaskState(taskId, view.isChecked)
            }
        }

    }

    fun updatePriority(view: View) {
        if (view is MaterialCheckBox) {
            viewModelScope.launch {
                taskRepository.updateTaskPriority(taskId, view.isChecked)
            }
        }
    }

    fun updateDueDate(taskId: Long, dueDate: Long) {
        viewModelScope.launch {
            taskRepository.updateDueDate(taskId, dueDate)
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            taskRepository.deleteTask(taskId)
        }
    }


}


class TaskViewModelFactory(
    private val taskRepository: TaskRepository,
    private val taskId: Long,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(taskRepository, taskId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}