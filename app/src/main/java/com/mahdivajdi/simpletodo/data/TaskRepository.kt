package com.mahdivajdi.simpletodo.data

import com.mahdivajdi.simpletodo.data.local.dao.TaskDao
import com.mahdivajdi.simpletodo.data.local.dao.toDomainModel
import com.mahdivajdi.simpletodo.data.local.mapper.TaskMapper.toEntity
import com.mahdivajdi.simpletodo.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val taskLocalSource: TaskDao) {

    fun getTasks(): Flow<List<Task>> =
        taskLocalSource.getTasks().map { taskList ->
            taskList.map {
                it.toDomainModel()
            }
        }

    fun getTask(id: Long) =
        taskLocalSource.getTask(id).map {
           it.toDomainModel()
        }

    suspend fun insertTask(task: Task) =
        taskLocalSource.insertTask(task.toEntity())

    suspend fun updateTask(task: Task) =
        taskLocalSource.updateTask(task.toEntity())

    suspend fun deleteTask(task: Task) =
        taskLocalSource.deleteTask(task.toEntity())
}