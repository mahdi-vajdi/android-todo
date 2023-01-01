package com.mahdivajdi.simpletodo.ui.task

import androidx.lifecycle.*
import com.mahdivajdi.simpletodo.data.local.mapper.CategoryMapper.toDomain
import com.mahdivajdi.simpletodo.data.local.mapper.CategoryMapper.toEntity
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.domain.model.Category
import com.mahdivajdi.simpletodo.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class TaskViewModel(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

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

    fun getCategories(): LiveData<List<Category>> = categoryRepository.getCategories().asLiveData()

    fun getCategory(categoryId: Long): LiveData<Category> =
        categoryRepository.getCategory(categoryId).asLiveData()

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
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(
                taskRepository,
                categoryRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

