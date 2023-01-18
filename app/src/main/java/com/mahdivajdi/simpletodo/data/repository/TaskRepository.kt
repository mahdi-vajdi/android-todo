package com.mahdivajdi.simpletodo.data.repository

import com.mahdivajdi.simpletodo.data.local.dao.TaskDao
import com.mahdivajdi.simpletodo.data.local.mapper.TaskMapper.toDomain
import com.mahdivajdi.simpletodo.data.local.mapper.TaskMapper.toEntity
import com.mahdivajdi.simpletodo.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val dataSource: TaskDao) {

    fun getTasks(): Flow<List<Task>> =
        dataSource.getTasks().map { taskList ->
            taskList.map {
                it.toDomain()
            }
        }

    fun getTask(id: Long) =
        dataSource.getTask(id).map {
           it.toDomain()
        }

    fun getTasksByCategoryId(categoryId: Long) =
        dataSource.getTasksWithCategoryId(categoryId).map { taskList ->
            taskList.map { taskEntity ->
                taskEntity.toDomain()
            }
        }

    fun getPriorityTasks() = dataSource.getPriorityTasks().map { taskList ->
        taskList.map {
            it.toDomain()
        }
    }

    fun getTasksWithDate(date: Long) = dataSource.getTasksWithDate(date).map { taskList ->
        taskList.map {
            it.toDomain()
        }
    }

    suspend fun insertTask(task: Task) =
        dataSource.insertTask(task.toEntity())

    suspend fun updateTask(task: Task) =
        dataSource.updateTask(task.toEntity())

    suspend fun toggleTaskState(taskId: Long) =
        dataSource.toggleTaskState(taskId)

    suspend fun updateTaskTitle(taskId: Long, title: String) =
        dataSource.updateTaskTitle(taskId, title)

    suspend fun updateTaskDetail(taskId: Long, detail: String) =
        dataSource.updateTaskDetail(taskId, detail)

    suspend fun toggleTaskPriority(taskId: Long) =
        dataSource.toggleTaskPriority(taskId)

    suspend fun updateDueDate(taskId: Long, dueDate: Long) =
        dataSource.updateDueDate(taskId, dueDate)

    suspend fun deleteTask(taskId: Long) =
        dataSource.deleteTask(taskId)
}