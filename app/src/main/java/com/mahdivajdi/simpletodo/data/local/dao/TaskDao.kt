package com.mahdivajdi.simpletodo.data.local.dao

import androidx.room.*
import com.mahdivajdi.simpletodo.data.local.entity.TASK_TABLE_NAME
import com.mahdivajdi.simpletodo.data.local.entity.TaskEntity
import com.mahdivajdi.simpletodo.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM $TASK_TABLE_NAME")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE task_id = :id")
    fun getTask(id: Long): Flow<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity): Long

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskId: Long)
    
}

