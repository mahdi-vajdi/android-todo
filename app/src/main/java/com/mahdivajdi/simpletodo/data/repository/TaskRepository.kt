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

    suspend fun insertTask(task: Task) =
        dataSource.insertTask(task.toEntity())

    suspend fun updateTask(task: Task) =
        dataSource.updateTask(task.toEntity())

    suspend fun deleteTask(taskId: Long) =
        dataSource.deleteTask(taskId)
}