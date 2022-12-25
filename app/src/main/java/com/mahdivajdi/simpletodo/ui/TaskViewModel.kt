package com.mahdivajdi.simpletodo.ui

import androidx.lifecycle.*
import com.mahdivajdi.simpletodo.data.TaskRepository
import com.mahdivajdi.simpletodo.domain.model.TaskDomainModel
import kotlinx.coroutines.launch


class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    fun getTasks(): LiveData<List<TaskDomainModel>> =
        taskRepository.getTasks().asLiveData()

    fun getTask(id: Int) =
        taskRepository.getTask(id)

    fun insertTask(task: TaskDomainModel) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }

    fun updateTask(task: TaskDomainModel) = viewModelScope.launch {
        taskRepository.updateTask(task)
    }

    fun deleteTask(task: TaskDomainModel) = viewModelScope.launch {
        taskRepository.deleteTask(task)
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

