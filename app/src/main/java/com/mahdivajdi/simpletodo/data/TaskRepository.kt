package com.mahdivajdi.simpletodo.data

import com.mahdivajdi.simpletodo.data.local.TaskDao
import com.mahdivajdi.simpletodo.data.local.TaskLocalModel
import com.mahdivajdi.simpletodo.data.local.toDomainModel
import com.mahdivajdi.simpletodo.domain.model.TaskDomainModel
import com.mahdivajdi.simpletodo.domain.model.toLocalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val taskLocalSource: TaskDao) {

    fun getTasks(): Flow<List<TaskDomainModel>> =
        taskLocalSource.getTasks().map { taskList ->
            taskList.map {
                it.toDomainModel()
            }
        }

    fun getTask(id: Int) =
        taskLocalSource.getTask(id)

    suspend fun insertTask(task: TaskDomainModel) =
        taskLocalSource.insertTask(task.toLocalModel())

    suspend fun updateTask(task: TaskDomainModel) =
        taskLocalSource.updateTask(task.toLocalModel())

    suspend fun deleteTask(task: TaskDomainModel) =
        taskLocalSource.deleteTask(task.toLocalModel())
}