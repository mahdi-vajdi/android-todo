package com.mahdivajdi.simpletodo.ui.task

import androidx.lifecycle.*
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.domain.model.Task
import kotlinx.coroutines.launch


class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    fun getTasks(): LiveData<List<Task>> =
        taskRepository.getTasks().asLiveData()

    fun getTask(id: Long) =
        taskRepository.getTask(id).asLiveData()

    fun insertTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task)
    }

    fun deleteTask(taskId: Long) = viewModelScope.launch {
        taskRepository.deleteTask(taskId)
    }
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

