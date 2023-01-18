package com.mahdivajdi.simpletodo.ui

import androidx.lifecycle.*
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.domain.model.Category
import com.mahdivajdi.simpletodo.domain.model.Task
import kotlinx.coroutines.launch


class MainViewModel(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    fun getTasks(): LiveData<List<Task>> =
        taskRepository.getTasks().asLiveData()

    fun getTask(id: Long) =
        taskRepository.getTask(id).asLiveData()

    fun getPriorityTasks(): LiveData<List<Task>> =
        taskRepository.getPriorityTasks().asLiveData()

    fun getTasksWithDate(date: Long): LiveData<List<Task>> =
        taskRepository.getTasksWithDate(date).asLiveData()

    fun insertTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task)
    }

    fun updateTaskTitle(taskId: Long, title: String) = viewModelScope.launch {
        taskRepository.updateTaskTitle(taskId, title)
    }

    fun updateTaskDetail(taskId: Long, detail: String) = viewModelScope.launch {
        taskRepository.updateTaskDetail(taskId, detail)
    }
    fun toggleTaskState(taskId: Long) = viewModelScope.launch {
        taskRepository.toggleTaskState(taskId)
    }

    fun toggleTaskPriority(taskId: Long) = viewModelScope.launch {
        taskRepository.toggleTaskPriority(taskId)
    }

    fun updateDueDate(taskId: Long, dueDate: Long) = viewModelScope.launch {
        taskRepository.updateDueDate(taskId, dueDate)
    }

    fun deleteTask(taskId: Long) = viewModelScope.launch {
        taskRepository.deleteTask(taskId)
    }

    fun getCategories(): LiveData<List<Category>> = categoryRepository.getCategories().asLiveData()

    fun getCategory(categoryId: Long): LiveData<Category> =
        categoryRepository.getCategory(categoryId).asLiveData()

    fun getTaskByCategoryId(categoryId: Long) =
        taskRepository.getTasksByCategoryId(categoryId).asLiveData()

    fun insertCategory(category: Category) = viewModelScope.launch {
        categoryRepository.insertCategory(category)
    }

    fun updateCategory(category: Category) = viewModelScope.launch {
        categoryRepository.updateCategory(category)
    }

    fun deleteCategory(categoryId: Long) = viewModelScope.launch {
        categoryRepository.deleteCategory(categoryId)
    }
}

class TaskViewModelFactory(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                taskRepository,
                categoryRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

