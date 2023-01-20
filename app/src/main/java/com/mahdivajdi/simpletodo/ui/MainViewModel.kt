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

    fun getPriorityTasks(): LiveData<List<Task>> =
        taskRepository.getPriorityTasks().asLiveData()

    fun getTasksWithDate(date: Long): LiveData<List<Task>> =
        taskRepository.getTasksWithDate(date).asLiveData()

    fun insertTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }

    fun updateTaskState(taskId: Long, isChecked: Boolean) = viewModelScope.launch {
        taskRepository.updateTaskState(taskId, isChecked)
    }

    fun updateTaskPriority(taskId: Long, isChecked: Boolean) = viewModelScope.launch {
        taskRepository.updateTaskPriority(taskId, isChecked)
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

class MainViewModelFactory(
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

